import {Component, ViewChild} from "@angular/core";

import {ChartComponent} from "ng-apexcharts";

export type ChartOptions = {
  series: any;
  chart: any;
  dataLabels: any;
  fill: any;
  colors: any;
  title: any;
  xaxis: any;
  grid: any;
  plotOptions: any;
};
@Component({
  selector: 'app-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.scss']
})
export class HeatmapComponent {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: Partial<ChartOptions>;

  constructor() {
    this.chartOptions = {
      series: [
        {
          name: "Metric1",
          data: this.generateData(18, {
            min: 0,
            max: 90
          })
        },
        {
          name: "Metric2",
          data: this.generateData(18, {
            min: 0,
            max: 90
          })
        },
        {
          name: "Metric3",
          data: this.generateData(18, {
            min: 0,
            max: 90
          })
        },
        {
          name: "Metric4",
          data: this.generateData(18, {
            min: 0,
            max: 90
          })
        },
        {
          name: "Metric5",
          data: this.generateData(18, {
            min: 0,
            max: 90
          })
        }
      ],
      chart: {
        height: 200,
        type: "heatmap" ,
        toolbar: {
          show: false,
        }
      },
      dataLabels: {
        enabled: false
      },
      colors: ["#0275d8"]
    };
  }

  public generateData(count:any, yrange:any) {
    var i = 0;
    var series = [];
    while (i < count) {
      var x = "jan " + (i + 1).toString();
      var y =
        Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

      series.push({
        x: x,
        y: y
      });
      i++;
    }
    return series;
  }
}
