import { Component, ViewChild,OnInit, Output, EventEmitter } from '@angular/core';
import { Person } from 'src/app/common/person';
import { PersonService } from 'src/app/services/person.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-person-new',
  templateUrl: './person-new.component.html',
  styleUrls: ['./person-new.component.css']
})
export class PersonNewComponent implements OnInit {


  @Output() miEvento = new EventEmitter<Person>();

  formValue!: FormGroup;

  allstudent: any;

  btnUpdateShow: boolean = false;

  btnSaveShow: boolean = true;


  personobj: Person = {
    personId:0,
    sharedKey: '',
    bussinesId: '',
    mobileNumber: 0,
    email: '',
    createdAt: '',
    updatedAt:''
  };
  submitted = false;

  constructor(private staticBackdrop: NgbModal,private formBuilder: FormBuilder, private personService: PersonService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formValue = this.formBuilder.group({
      sharedKey: [''],
      bussinesId: [''],
      email: [''],
      mobileNumber: [''],
      createdAt: ['']
    })
    //this.cargarPersons();
  }


  AddPerson() {
    this.personobj.sharedKey = this.formValue.value.sharedKey;
    this.personobj.bussinesId = this.formValue.value.bussinesId;
    this.personobj.mobileNumber = this.formValue.value.mobileNumber;
    this.personobj.email = this.formValue.value.email;
    this.personobj.createdAt = this.formValue.value.createdAt;

    this.personService.save(this.personobj).subscribe({
      next: (v) => { console.log(v) },
      error: (e) => {
        alert("Error "+ e.error.mensaje);
        console.log(e)
      },
      complete: () => {
        console.log('complete')
        alert("Data Saved")
        this.formValue.reset();
        this.staticBackdrop.dismissAll();
        this.miEvento.emit(this.personobj);
      }
    })

  }

}
