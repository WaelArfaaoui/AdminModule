import {Component, ViewChild} from '@angular/core';
import {ChartComponent} from "ng-apexcharts";
import {CategoryDto, CategoryService} from "../../../open-api";
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
  categories!: CategoryDto[];


    constructor( private categoryService: CategoryService) {
    this.chartOptions = {
      series: [30, 30, 12, 15, 13],
      chart: {
        type: "pie"
      },
      dataLabels: {
        enabled: false
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
    ngOnInit(): void {
        this.loadCategories();
    }
    loadCategories() {
        this.categoryService.getAllCategories().subscribe({
            next: data => {
                this.categories = data;
            },
            error: error => {
                console.error('Error loading categories:', error);
            }
        });
    }
}
