<?php

namespace App\Models;




class User
{
    
    protected $id;
    
    protected $empid;

    protected $name;
    
    protected $email;

    protected $location;

    protected $designation;

    protected $managerId;
    
    protected $department;

    public function __construct($id , $empid , $name , $email , $location , $designation , $managerId , $department) {
            $this->id =  $id;
            $this->empid = $empid;
            $this->name = $name;
            $this->email = $email;
            $this->location= $location;
            $this->designation = $designation;
            $this->managerId  =    $managerId;
            $this->department = $department;

    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getEmpId(): ?int
    {
        return $this->empid;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    

    public function getLocation(): ?string
    {
        return $this->location;
    }

    public function setLocation(string $location): self
    {
        $this->location = $location;

        return $this;
    }

    public function getDesignation(): ?string
    {
        return $this->designation;
    }

    public function setDesignation(string $designation): self
    {
        $this->designation = $designation;

        return $this;
    }

    public function getManagerId(): ?int
    {
        return $this->managerId;
    }

    public function setManagerId(int $managerId): self
    {
        $this->managerId = $managerId;

        return $this;
    }

    public function getAllProperties(): array {
        return get_object_vars($this);
       
    }
}
