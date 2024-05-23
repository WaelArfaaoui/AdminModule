import { Component, OnInit } from '@angular/core';
import {LayoutService} from "../../layout/service/app.layout.service";
import {Subscription} from "rxjs";
import {TableService} from "../../services/table/table.service";
import {StackedColumnsComponent} from "../stacked-columns/stacked-columns.component";
@Component({
  selector: 'app-dashbord',
  templateUrl: './dashbord.component.html',
  styleUrls: ['./dashbord.component.scss']
})
export class dashbordComponent implements OnInit {
tablenumberupdates=1;
heatMapdata=[{x:"",y:""}];
  subscription: Subscription;
  constructor(public layoutService: LayoutService,public tableService:TableService) {
    this.subscription = this.layoutService.configUpdate$.subscribe(config => {

    });
  }
  ngOnInit(): void {
    this.dataforTreeMap()
  }
  dataforTreeMap(){
    this.tableService.paramTableTreemap().subscribe({
        next:(response:any)=>
        {
          this.tablenumberupdates=response.numberupdates;
          this.heatMapdata=response.data;
          console.log("response")
        }
      }
    )
  }
  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
