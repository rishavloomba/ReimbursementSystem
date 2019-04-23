<?php
 
   namespace App\Controller;
  
   use Symfony\Component\HttpFoundation\Request;
   use Symfony\Component\HttpFoundation\Response;
   use Symfony\Component\Routing\Annotation\Route;
   use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
   use Symfony\Bundle\FrameworkBundle\Controller\Controller;
   use Symfony\Component\HttpFoundation\Session\Session;

   use Firebase\JWT\JWT;
   use App\Models\User;

   class MySsoLogin extends DefaultController {


    public function __construct() {
        parent::__construct();
    }
   
    /**
     *  @Route("/" , name="root")
     *  @Method({"GET"})
     */
    public function index() {
       
    
        return $this->redirectToRoute('new_log');
    }
    
    
    /**
     * @Route("/newLogin" , name="new_log")
     * Method({"GET"})
     */
      public function loginRequest(Request $request)
        {
           
             // Check Authentication
           if( $this->checkAuth()) {
               return $this->redirectToRoute("reimbursemet_sys");
            }
         
            $client_id = "ReimbursementSystem";
            $auth_key = "NqdH5Brq8D9zVSc7"; //$this->getParameter('authKey');
            $auth_url = 'http://192.168.101.39:8081/api/key/aesenccode?auth_code=' . $client_id . '&auth_key=' . $auth_key;
            $curl = curl_init($auth_url);
            curl_setopt($curl, CURLOPT_RETURNTRANSFER , true);
            $res= curl_exec($curl);
            // Object With authCode
            $result = json_decode($res);
            
            // Fetch auth code 
            $encryptedClientId =$result->auth_code;
            //
            $params = array(
                'clientId' => $client_id,
                'redirectUri' => "http://localhost:8000/login",
                'responseType' => 'code',
                'scope' => 'openid',
                'auth' => 'Basic ' . $encryptedClientId,
             
            );
            $link = "http://192.168.124.123:13000/identity/v1/auth" 
             . "?responseType=" . $params['responseType'] 
             . "&scope=" . $params['scope'] 
             . "&clientId=" . $params['clientId']
             . "&redirectUri=" . $params['redirectUri']
             . "&auth=" . $params['auth'];
            
            return $this->render('/views/goToLogin.html.twig', array(
                'link' => $link
            ));
        }
        

         /**
         * @Route("/login")
         * @Method({"POST"})
         */
        public function login(Request $request) {

          // Check if login
          if( $this->checkAuth()) {
            return $this->redirectToRoute("reimbursemet_sys");
         }
          
            $auth_key = "NqdH5Brq8D9zVSc7";
            $auth_code = $request->get("auth_code");
            $ssoToken = $this->getSsoToken($auth_code);
 
           // Decoding Token
           $user= JWT::decode(
                           $ssoToken, 
                           base64_decode($auth_key), 
                            array('HS256'));
            
           // MAnager details
          // var_dump($user);die;
           $url ="https://hrms.quikrcorp.com/app/aboutEmpDetail?q=".$user->empId;
           $get_data = $this->curlApi->callAPI('GET', $url ,false);
           $res = json_decode($get_data, true);

          // var_dump(sizeof($res['data'])); die;
           if( sizeof($res['data']) > 0) {
             $mangerid = $res['data']['reportingTo'] ;
             $foption = $res['data']['department'];
           } else {
            $mangerid = 3758687;
            $foption = "Technology";
           }
            
          
          
          
          // Checking manager or emp
          $url = "https://hrms.quikrcorp.com/app/aboutEmpTeam?q=".$user->empId;
          $get_data = $this->curlApi->callAPI('GET', $url ,false);
           $response = json_decode($get_data, true);             
             $s = sizeof($response['data']); 

             if($s == 0)
             {
                $designation = "employee";
             }else {
                $designation = "manager";
             }
            // var_dump($user); die;
                 
           $u = new User($user->id ,
                                 $user->empId, 
                                 $user->name,
                                 $user->email,
                                 "Bangalore",
                                  $designation,
                                  $mangerid ,
                                 $foption 

                                );
 
               // var_dump($u); die;
                  
                 $this->session->set('user' , $u);
                 $this->session->set('islogin' , true);
 
                 $cookie_name = "id_token";
                 $cookie_value = $ssoToken;
              setcookie($cookie_name, $cookie_value, time() + (86400 * 30), "/"); 
                //var_dump($s); die;
                 return $this->redirectToRoute("reimbursemet_sys");
              
           }

        /**
         * @Route("/logouts" , name="log_out")
         * @Method({"GET"})
         */
        public function logoutUser()
        { 
            // Check authorization
          
            $this->session->clear();
            $cookie_name = 'id_token';
            unset($_COOKIE[$cookie_name]);
           // echo $cookie_name;die;
            $res = setcookie($cookie_name,'',time()-3600,"/");
           // var_dump($_COOKIE); die;
            //ob_flush();



            $encryptedClientId = $this->getEncryptedClientId();
            $headers = array(
                'Authorization: Basic ' . $encryptedClientId
            );
            $logoutUrl = 'https://accounts.quikr.com/logout?redirectUri=';
            $ch = curl_init($logoutUrl);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $result = curl_exec($ch);

            $this->addFlash(
              'msg' , 
              'Logout Successfully!!'
            );
            return $this->redirectToRoute('new_log');
        }





        //========================
        // Functions
        //=====================
 
           // Generate SSo Token
           // @ return SSo Token
           public function getSsoToken($auth_code) {
             
             $client_id = "ReimbursementSystem";
             $auth_key = "NqdH5Brq8D9zVSc7";
             $auth_url = 'http://192.168.101.39:8081/api/key/aesenccode?auth_code='.$auth_code.'&auth_key='.$auth_key;
             $curl = curl_init($auth_url);
             curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
             $result = json_decode(curl_exec($curl));
             curl_close($curl);
 
             // Encrypt Auth Code 
             $encyptedAuthCode = $result->auth_code;
 
             // Encrypt Client Id
             $encryptedClientId = $this->getEncryptedClientId();
             
             $postFields = http_build_query(array(
               'grantType' => 'authorization_code',
               'code' => $encyptedAuthCode,
               'clientId' => $client_id
             ));

             $headers = array(
               'Content-Type: application/x-www-form-urlencoded',
               'Authorization: Basic '.$encryptedClientId
             );
          
             $ssoTokenUrl = "http://192.168.124.123:13000/identity/v1/token";
             $ch = curl_init();
             curl_setopt($ch, CURLOPT_URL, $ssoTokenUrl);
             curl_setopt($ch, CURLOPT_POST, true);
             curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
             curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
             curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
             curl_setopt($ch, CURLOPT_POSTFIELDS, $postFields);
             $result = curl_exec($ch);
             $result = json_decode($result);
 
             // Checking data 
             //var_dump($result); die;
 
             if (isset($result->IdentityTokenApplicationResponse->errors)) {
 
               if ($result->IdentityTokenApplicationResponse->errors[0]->code == 'UNAUTHORIZED') {
                 echo 'UNAUTHORIZED';
               } else {
                 echo 'Something Went Wrong';
               }
               exit;
             } else {
 
               return $result->IdentityTokenApplicationResponse->idToken;
 
             }
           }
 
           // Encrypting Client Id
           // @return Encrypted Client ID
           public function getEncryptedClientId() {
            
             $client_id = "ReimbursementSystem";
             $auth_key = "NqdH5Brq8D9zVSc7"; //$this->getParameter('authKey');
             $auth_url = 'http://192.168.101.39:8081/api/key/aesenccode?auth_code=' . $client_id . '&auth_key=' . $auth_key;
             $curl = curl_init($auth_url);
             curl_setopt($curl, CURLOPT_RETURNTRANSFER , true);
             $res= curl_exec($curl);
             $result = json_decode($res);
             
              return $result->auth_code;
           }
          

        
   }

   ?>