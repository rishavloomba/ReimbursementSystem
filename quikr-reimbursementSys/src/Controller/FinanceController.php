<?php 
  namespace App\Controller;
   
  
 use Symfony\Component\HttpFoundation\Request;
 use Symfony\Component\HttpFoundation\Response;
 use Symfony\Component\Routing\Annotation\Route;
 use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
 use Symfony\Bundle\FrameworkBundle\Controller\Controller;

 class FinanceController extends DefaultController {
      
      
       /**
        * @Route("/finance" ,name = "finanace_view")
        * @Method({"GET"})
        */
        public function finance()
        {
          
          // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
          }
           
          $user = $this->session->get('user')->getAllProperties();

          

           $get_data = $this->curlApi->callAPI('GET', 'localhost:8080/Forms/Finance', false);
           $response = json_decode($get_data, true);
         //  print_r($response);die;
            
           return $this->render('views/financeView.html.twig', array(

             "result" => $response,
             "emp"   => $user
          ));
        }

        /**
        * @Route("/finance/view/{fid}" ,name = "details")
        * @Method({"GET"})
        */
        public function financeview($fid)
        {
          
          // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
          }
           
          
          $user = $this->session->get('user')->getAllProperties();
        
            $data = array(
              "formid" => $fid
              

          );
          

            $urlString = 'localhost:8080/Forms/Tasks';
            $get_data = $this->curlApi->callAPI('GET', $urlString , json_encode($data));
            $response = json_decode($get_data, true);
          
            // print_r($response);die;
            $key =  array_keys($response);


          // Fetching task data  
          
              return $this->render('views/detailview.html.twig', array(
              
                "result" => $response,
                "emp"   => $user,
                
            ));
        }

        /**
         * @Route("/finance/payment/{fid}" , name="payment")
         * @Method({"POST"})
         */
        public function pay($fid)
        {
          // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
          }
           
          
          $user = $this->session->get('user')->getAllProperties();

          
          
            
            $body= array(
              "financeid"=>$user['empid'],
              "formid"=>$fid
            );

          $urlString='localhost:8080/Forms/Finance/Pay';
          $get_data = $this->curlApi->callAPI('GET',$urlString,json_encode($body));

          $get_data = $this->curlApi->callAPI('GET', 'localhost:8080/Forms/Finance', false);
          $response = json_decode($get_data, true);
          

          return $this->render('views/financeView.html.twig', array(

            "result" => $response,
            "emp"   => $user
         ));
        }

        /**
         * @Route("/finance/paidview" , name="paidview")
         * @Method({"GET"})
         */
        public function paidview()
        { 
          // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
          }
           
      
      // $eid = $this->session->get('empid');
      // $get_data =   $this->curlApi->callAPI('GET', 'localhost:8080/login/'.$eid , false);
      // $emp = json_decode($get_data, true); 
      $user = $this->session->get('user')->getAllProperties();
      
      $data=array(
        "financeid" =>$user['empid']
      );

       $get_data = $this->curlApi->callAPI('GET', 'localhost:8080/Forms/Finance/History', json_encode($data));
       $response = json_decode($get_data, true);
       //print_r($response);die;
        
       return $this->render('views/paidview.html.twig', array(

         "result" => $response,
         "emp"   => $user
      ));

 }

   /**
    * @Route("/finance/filter", name="finance_filter")
    * @Method({"POST"})
    */
    public function financeFilter(Request $request) {

              // Check Authentication
              if( !$this->checkAuth()) {
                return $this->redirectToRoute('new_log');
              }
           

      
          // $eid = $this->session->get('empid');
          // $get_data =   $this->curlApi->callAPI('GET', 'localhost:8080/login/'.$eid , false);
          // $emp = json_decode($get_data, true); 

          $user = $this->session->get('user')->getAllProperties();

          $d = array_merge(array( "financeid" => 0) , $request->request->all());
         //  print_r($d);die;
          $url = 'localhost:8080/Forms/Finance/History';
          $get = $this->curlApi->callAPI('GET' , $url , json_encode($d));
          $response = json_decode($get , true);
        //  print_r($response);die;
          return $this->render("/views/paymenthistory.html.twig",array(
            "res"=>$get,

             "result" => $response,
             "emp"   => $user
          ));

        }

   /**
    * @Route("/finance/history" , name="history")
    * @Method({"GET"})
    */
    public function history()
    { 
          // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
          }
      
      // $eid = $this->session->get('empid');
      // $get_data =   $this->curlApi->callAPI('GET', 'localhost:8080/login/'.$eid , false);
      // $emp = json_decode($get_data, true); 
      
      $user = $this->session->get('user')->getAllProperties();
          $body= array(
            "financeid"=>0
          );

      
      $get_data=$this->curlApi->callAPI('GET','localhost:8080/Forms/Finance/History',json_encode($body));
      $final=json_decode($get_data);
         // print_r($final);die;
      return $this->render('views/paymenthistory.html.twig', array(
        "res"=>$get_data,
        "result"=>$final,
        "emp"=> $user
      ));
    }

       

 }
?>

