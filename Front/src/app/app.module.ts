import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppLayoutModule } from './layout/app.layout.module';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
import {FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { ToastModule } from 'primeng/toast';
import { StepsModule } from 'primeng/steps';
import { MessageService } from 'primeng/api';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { CheckboxModule } from 'primeng/checkbox';
import { PasswordModule } from 'primeng/password';
import {CommonModule} from "@angular/common";
import {TableModule} from "primeng/table";
import {FileUploadModule} from "primeng/fileupload";
import {ButtonModule} from "primeng/button";
import {ToolbarModule} from "primeng/toolbar";
import {RippleModule} from "primeng/ripple";
import {RatingModule} from "primeng/rating";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {DropdownModule} from "primeng/dropdown";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputNumberModule} from "primeng/inputnumber";
import {DialogModule} from "primeng/dialog";
import {Overlay, OverlayModule} from "primeng/overlay";
import { AllRulesComponent } from './components/all-rules/all-rules.component';
import { NewRuleComponent } from './components/new-rule/new-rule.component';
import {MultiSelectModule} from "primeng/multiselect";
import {SliderModule} from "primeng/slider";
import {ToggleButtonModule} from "primeng/togglebutton";
import { LoginComponent } from './pages/login/login.component';
import { ParamTableComponent } from './components/param-table/param-table.component';
import { DashbordComponent } from './components/dashbord/dashbord.component';
import {ChartModule} from "primeng/chart";
import { AddUserComponent } from './components/add-user/add-user.component';

@NgModule({
  declarations: [AppComponent, AllUsersComponent , AllRulesComponent, NewRuleComponent, LoginComponent, ParamTableComponent, DashbordComponent, AddUserComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppLayoutModule,
    MatInputModule,
    MatStepperModule,
    ReactiveFormsModule,
    MatButtonModule,
    ToastModule,
    StepsModule,
    CheckboxModule,
    PasswordModule,
    FormsModule,
    CommonModule,
    TableModule,
    FileUploadModule,
    ButtonModule,
    ToolbarModule,
    RippleModule,
    RatingModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    DialogModule,
    OverlayModule,
    MultiSelectModule,
    SliderModule,
    ToggleButtonModule,
    ChartModule,

  ],
  providers: [MessageService],
  bootstrap: [AppComponent],
})
export class AppModule {}
