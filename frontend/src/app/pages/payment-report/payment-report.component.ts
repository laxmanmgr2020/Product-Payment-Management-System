import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AxiosService} from "../../axios.service";
import * as d3 from 'd3';

@Component({
  selector: 'app-payment-report',
  templateUrl: './payment-report.component.html',
  styleUrl: './payment-report.component.css'
})
export class PaymentReportComponent implements OnInit {
  public paymentData: { name: string; value: number }[] = [];

  @ViewChild('chart') chartContainer!: ElementRef;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/admin/paymentReport"
    ).then(
      response => {
        this.paymentData = response.data.paymentReport.map((item: any) => ({
          name: item.name,  // Mapping "category" to "name"
          value: item.value// Mapping "amount" to "value"
        }));
        // this.createChart(this.paymentData);
        this.createPieChart(this.paymentData);
      }).catch(
      error => {
        console.error('Payment report fetch failed', error);
      }
    );
  }

  private createPieChart(data: { name: string, value: number }[]): void {
    const element = this.chartContainer.nativeElement;
    d3.select(element).select('svg').remove(); // Remove previous chart if any

    const width = 400, height = 400, radius = Math.min(width, height) / 2;

    const svg = d3.select(element)
      .append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', `translate(${width / 2}, ${height / 2})`); // Center pie chart

    // 🎯 Step 1: Define color scale
    const color = d3.scaleOrdinal(d3.schemeCategory10);

    // 🎯 Step 2: Define Pie Generator
    const pie = d3.pie<{ name: string, value: number }>()
      .value(d => d.value)
      .sort(null);

    const arc = d3.arc<d3.PieArcDatum<{ name: string, value: number }>>()
      .innerRadius(0)  // Full pie, no donut hole
      .outerRadius(radius);

    // 🎯 Step 3: Create a tooltip
    const tooltip = d3.select('body')
      .append('div')
      .attr('class', 'tooltip')
      .style('position', 'absolute')
      .style('background', 'rgba(0, 0, 0, 0.7)')
      .style('color', '#fff')
      .style('padding', '5px 10px')
      .style('border-radius', '5px')
      .style('font-size', '12px')
      .style('display', 'none')
      .style('pointer-events', 'none');

    // 🎯 Step 4: Draw Pie Chart
    const slices = svg.selectAll('path')
      .data(pie(data))
      .enter()
      .append('path')
      .attr('d', arc)
      .attr('fill', d => color(d.data.name))
      .attr('stroke', '#fff')
      .style('stroke-width', '2px')
      .on('mouseover', function (event, d) {
        d3.select(this).attr('opacity', 1); // Highlight slice on hover
        tooltip
          .style('display', 'block')
          .html(`<strong>${d.data.name}</strong>: ${d.data.value}`);
      })
      .on('mousemove', function (event) {
        tooltip
          .style('left', `${event.pageX + 10}px`)
          .style('top', `${event.pageY - 20}px`);
      })
      .on('mouseout', function () {
        d3.select(this).attr('opacity', 1); // Reset opacity
        tooltip.style('display', 'none');
      });

    // 🎯 Step 5: Add Labels (Optional)
    const text = svg.selectAll('text')
      .data(pie(data))
      .enter()
      .append('text')
      .attr('transform', d => `translate(${arc.centroid(d)})`)
      .attr('text-anchor', 'middle')
      .attr('font-size', '14px')
      .attr('fill', '#fff')
      .text(d => d.data.name) // Display the name by default
      .on('mouseover', function (event, d) {
        tooltip.transition().duration(20).style('opacity', 1);
        tooltip.html(`Value: ${d.value}`)
          .style('left', `${event.pageX + 5}px`)
          .style('top', `${event.pageY - 20}px`);
      })
      .on('mousehover', function () {
        // Hide tooltip on mouseout
        tooltip.transition().duration(200).style('opacity', 0);
      });
  }

}
