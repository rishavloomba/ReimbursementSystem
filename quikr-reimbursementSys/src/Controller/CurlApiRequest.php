<?php 

namespace App\Controller;

Class CurlApiRequest {

      public function __construct() {
      //echo "##########################Object created######################################";
      }
     // ------------------ api request ------------------------------------
     public function callAPI($method, $url, $data ){
       
        $curl = curl_init();
     
        switch ($method){
           case "POST":
              curl_setopt($curl, CURLOPT_POST, 1);
              if ($data)
             // print_r($data);die;
                 curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
              break;
           case "PUT":
              curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT");
              if ($data)
                 curl_setopt($curl, CURLOPT_POSTFIELDS, $data);			 					
              break;
            case "GET":
               curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "GET");
                 if ($data)
                     curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
                 break;
           default:
              if ($data)
                 $url = sprintf("%s?%s", $url, http_build_query($data));
        }
     
        // OPTIONS:
        curl_setopt($curl, CURLOPT_URL, $url);
         curl_setopt($curl, CURLOPT_HTTPHEADER, array(
            'APIKEY: 1',
           
            'Content-Type: application/json',

         ));
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
       curl_setopt($curl, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
     
        // EXECUTE:
       // print_r($curl);die;
        $result = curl_exec($curl);
        $res=json_decode($result);
       // var_dump(curl_error($curl));die;
       // var_dump($res);die;
      //   var_dump(json_encode(curl_getinfo($curl)));die;

       ///print_r($);
        if(!$result && $method != "POST"){
            return false;
        }
        curl_close($curl);
        return $result;
     }
}

?>