import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonListComponent } from './components/person/person-list/person-list.component';
import { HttpClientModule } from '@angular/common/http';
import { PersonService } from './services/person.service';

import { ReactiveFormsModule } from '@angular/forms';

import { PersonNewComponent } from './components/person/person-new/person-new.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PersonSearchComponent } from './components/person/person-search/person-search.component';



@NgModule({
  declarations: [
    AppComponent,
    PersonListComponent,
    PersonNewComponent,
    PersonSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
