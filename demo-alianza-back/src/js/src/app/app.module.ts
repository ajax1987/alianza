import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { PersonService } from './services/person.service';

import { AppComponent } from './app.component';
import { PersonListComponent } from './components/person/person-list/person-list.component';
import { PersonSearchComponent } from './components/person/person-search/person-search.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonListComponent,
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
