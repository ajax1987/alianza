import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonListComponent } from './components/person/person-list/person-list.component';


const routes: Routes = [
  {path: 'person', component: PersonListComponent},
  {path: 'search/:keyword', component: PersonListComponent},
  {path: '', redirectTo: '/person', pathMatch: 'full'},
  {path: '**', redirectTo: '/person', pathMatch: 'full'}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
