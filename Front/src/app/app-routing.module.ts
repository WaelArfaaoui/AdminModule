import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/app.layout.component';
import {AllUsersComponent} from "./components/all-users/all-users.component";
import {NewRuleComponent} from "./components/new-rule/new-rule.component";

import {ParamTableComponent} from "./components/param-table/param-table.component";
import {DashbordComponent} from "./components/dashbord/dashbord.component";

import {LoginComponent} from "./pages/login/login.component";
import {AllRulesComponent} from "./components/all-rules/all-rules.component";


const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      { path: '', component: DashbordComponent },
      { path: 'all', component: AllUsersComponent },
      { path: 'users', component: AllUsersComponent } ,

      { path: 'addrule', component: NewRuleComponent },

      { path: 'configtable', component: ParamTableComponent },

      { path: 'rules', component: AllRulesComponent }



    ]
  } ,
  {
    path: 'login',
    component: LoginComponent
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
