<?php
    namespace App\Controller;
  
    ob_start();
    use Symfony\Bundle\FrameworkBundle\Controller\Controller;
    use Symfony\Component\HttpFoundation\Session\Session;

    use App\Models\User;
    use Firebase\JWT\JWT;

    class DefaultController extends Controller {
        protected $session;
        protected $curlApi;

        public function __construct() {
            $this->curlApi = new CurlApiRequest();
            $this->session =  new Session();
            $this->session->start();
        }

        public function validateIdToken($ssoToken) {
             $ssoClientKey = "NqdH5Brq8D9zVSc7";
            try {
              $decodedToken = JWT::decode($ssoToken, base64_decode($ssoClientKey), array('HS256'));
              return $decodedToken;
            }
            catch (\Exception $e) {
                //echo "validated 2";
              return false;
            }
            
            // var_dump($decodedToken);exit;
          }

          public function checkAuth() {
              
            if (!isset($_COOKIE['id_token']) || !($this->validateIdToken($_COOKIE['id_token']))) {
               
               
              return false;
              
            } else {
                return true;
            }
            
          }

    }
?>