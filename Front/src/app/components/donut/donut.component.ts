import { Component, ViewChild } from '@angular/core';
import { ChartComponent } from "ng-apexcharts";

export type ChartOptions = {
  series: any;
  chart: any;
  responsive: any[];
  labels: any;
  dataLabels: any;
};

@Component({
  selector: 'app-donut',
  templateUrl: './donut.component.html',
  styleUrls: ['./donut.component.scss']
})
export class DonutComponent {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: Partial<ChartOptions>;

  constructor() {
    this.chartOptions = {
      series: [30, 30, 12, 15, 13],
      chart: {
        width: 330,
        type: "pie"
      },
      dataLabels: {
        enabled: false // This should hide the percentages
      },
      labels: ["Vehicles", "Real estate", "Industrial", "Medical", "Agricultural"],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            dataLabels: {
              enabled: false
            },
            legend: {
              show: false
            }
          }
        }
      ]
    };
  }
}
