<?php
namespace App\Controller;
   
  
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
   

class ReimbursementController extends DefaultController {

  /**
   *  @Route("/reimbursement", name="reimbursemet_sys")
   *  @Method({"GET"})
   */
   public function reimbursement() {
           
       // Check Authentication
        if( !$this->checkAuth()) {
           return $this->redirectToRoute('new_log');
         }
         $user = $this->session->get('user')->getAllProperties();
           
          $data = array(
                  "id" => $user['empid'] ,
                  "tofetch" => "all"

              );
           
          $urlString = 'localhost:8080/Forms';
          $get_data = $this->curlApi->callAPI('GET', $urlString , json_encode($data));
          $response = json_decode($get_data, true);
          
          // Key 
          $key =  array_keys($response);


          // Fetching task data  
          $final=array();
           $c = 0;
            foreach($key as $k) {
              foreach($response[$k] as $d ) {
                  
                $final[$k][] = $d['tasks'];
               }
              $c++;
           }
 


              // Fetching status
             $st = array();

             foreach($key as $k) {
              foreach($response[$k] as $d ) {
                  $st[$k] = $d['status'];
                     
               }
             }
      
             $user = $this->session->get('user')->getAllProperties();
             //var_dump($user); die;
            return $this->render('views/reimbursement.html.twig', array(
              "status" => $st,
               "key"  =>  $key,
                "result" => $final,
                "emp"   =>  $user
            ));
    }

     
     

    //=======================================
    // Published Expense
    //========================================        
      /**
       * @Route("reimbursement/publish/{tid}" , name="publishing")
       * @Method({"GET"})
       */
       public function publishedExp($tid) {
        
           // Check Authentication
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
           }
             
            $user = $this->session->get('user')->getAllProperties();
            
            $data = array(
                "id" => $user['empid'],
                "topublish" => [$tid]
            );
            $urlString = 'localhost:8080/tasks/emp/publish';
            $get_data = $this->curlApi->callAPI('POST', $urlString,  json_encode($data));
            
            return $this->redirectToRoute('reimbursemet_sys');
           
        }

        //========================
        //  Tasks Add Form
        //========================
        /**
         * @Route("/reimbursement/new" , name="new_reimbursement")
         * @Method({"GET"})
         */
        public function addNewReimbursement() {

           // Check Authenticatio
          if( !$this->checkAuth()) {
            return $this->redirectToRoute('new_log');
           }
            return $this->render("/views/addNewExpense.html.twig");
        }



        //============================
        //   add expense form 
        //============================
        /**
         *  @Route("/reimbursement/addexpense" , name="add_reimbursement")
         *  @Method({"POST"})
         */
    public function addNewReimbursementData(Request $request) {
           
          // Check Authentication
            if( !$this->checkAuth()) {
              return $this->redirectToRoute('new_log');
            }
   
        // $eid = $this->session->get('empid');
        // $get_data =   $this->curlApi->callAPI('GET', 'localhost:8080/login/'.$eid , false);
        // $emp = json_decode($get_data, true);

          // Fetch body from Request
          $raw = $request->request->all();
          
          // Checking Total
          $t = $raw['travel'][0] + $raw['hotel'][0]+ $raw['buisness'][0]+$raw['telephone'][0];
          
          // Checking is there any data enter or not
          if(!isset($raw['disc'])  ||  $t == 0) {
            $this->addFlash(
              'ins' , 
               'No Expense is Inserted!'
             );

             return $this->redirectToRoute('reimbursemet_sys');
          }
          $user = $this->session->get('user')->getAllProperties();
         
        $imgarr=[];
        
        $x=0;
          $size = sizeof($raw['date']);

          // File Uploading API
          foreach($_FILES['files']['tmp_name'] as $key => $tmp_name){
            $file_tmp = $_FILES['files']['tmp_name'][$key];
            $file_size=$_FILES['files']['size'][$key];
            $file_name = $_FILES['files']['name'][$key];
            $file_type=$_FILES['files']['type'][$key];
          
            $info = getimagesize($file_tmp);
            $f = fopen($file_tmp, "r");
            $string = base64_encode(fread($f, filesize($file_tmp)));
            $imagerawdata="data:" . $info["mime"] . ";base64," . $string;
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, "http://raven.kuikr.com/upload");
             curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query(array('imagerawdata' => $imagerawdata)));
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $result=json_decode(curl_exec($ch),true);
            //$curlResponse = curl_getinfo($ch);
            //print_r($result['fileurl']);die;
            curl_close($ch);
            unset($ch);
            $imgarr[$x]=$result['fileurl'];
            $x++;
          }
  
           
          $totalExp =0;
          $size = sizeof($raw['date']);
          for($i = 0 ; $i < $size ; $i++){
          $total = $raw["travel"][$i] + $raw['hotel'][$i]+ $raw['buisness'][$i]+$raw['telephone'][$i];
          $d =  array(
              "expense_date"  => $raw['date'][$i],
              "travel_exp"    => $raw['travel'][$i],
              "telephone_exp" => $raw['telephone'][$i],
              "hotel_stay"    => $raw['hotel'][$i],
              "business_meal" => $raw['buisness'][$i],
              "description"   => $raw['disc'][$i],
              "image_url"      => $imgarr[$i],

              "total_exp"     => $total
            );
              $totalExp = $totalExp + $total;
              $data[$i] = $d;
          }
          //var_dump($user['empid']);die;
         
          $final = array( 
            "empid"      => $user['empid'],
            "managerid"  => $user['managerId'],
            "tasks"      => $data,
            "total_exp"  => $totalExp
        );
          
        $get_data =  $this->curlApi->callAPI('POST', 'localhost:8080/Forms/AddForm' , json_encode($final));
        return $this->redirectToRoute('reimbursemet_sys');
        }

   }

   

 ?>  