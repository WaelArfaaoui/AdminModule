import { Component, Input, OnChanges, SimpleChanges, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import ApexCharts from 'apexcharts';

@Component({
  selector: 'app-stacked-columns',
  templateUrl: './stacked-columns.component.html',
  styleUrls: ['./stacked-columns.component.scss']
})
export class StackedColumnsComponent implements OnChanges, OnDestroy {
  @Input() heatMapdata: any[] = [];
  @ViewChild('chart', { static: true }) chart: ElementRef | undefined;

  chartOptions: any;
  apexChart: ApexCharts | undefined;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['heatMapdata']) {
      this.updateChartOptions();
    }
  }

  updateChartOptions() {
    this.chartOptions = {
      series: [{
        data: this.heatMapdata
      }],
      legend: {
        show: false
      },
      chart: {
        type: 'treemap'
      },
      title: {
        text: 'Parameter tables updates this year',
        align: 'center'
      },
      colors: [
        '#3B93A5', '#F7B844', '#ADD8C7', '#EC3C65', '#CDD7B6',
        '#C1F666', '#D43F97', '#1E5D8C', '#421243', '#7F94B0',
        '#EF6537', '#C0ADDB'
      ],
      plotOptions: {
        treemap: {
          distributed: true,
          enableShades: false
        }
      }
    };

    if (this.apexChart) {
      this.apexChart.destroy();
    }

    if (this.chart && this.chart.nativeElement) {
      this.apexChart = new ApexCharts(this.chart.nativeElement, this.chartOptions);
      this.apexChart.render();
    }
  }

  ngOnDestroy() {
    if (this.apexChart) {
      this.apexChart.destroy();
    }
  }
}
