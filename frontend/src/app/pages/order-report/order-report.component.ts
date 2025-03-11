import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AxiosService} from "../../axios.service";
import * as d3 from 'd3';

@Component({
  selector: 'app-order-report',
  templateUrl: './order-report.component.html',
  styleUrl: './order-report.component.css'
})

export class OrderReportComponent implements OnInit {
  public orderData: { name: string; value: number }[] = [];

  @ViewChild('chart') chartContainer!: ElementRef;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/admin/orderReport"
    ).then(
      response => {
        this.orderData = response.data.orderReport.map((item: any) => ({
          name: item.name,  // Mapping "category" to "name"
          value: item.value// Mapping "amount" to "value"
        }));
        this.createChart(this.orderData);
      }).catch(
      error => {
        console.error('Order report fetch failed', error);
      }
    );
  }

  private createChart(data: { name: string, value: number }[]): void {
    const element = this.chartContainer.nativeElement;
    d3.select(element).select('svg').remove(); // Remove previous chart

    const width = 500, height = 300, margin = { top: 20, right: 30, bottom: 40, left: 50 };

    const svg = d3.select(element)
      .append('svg')
      .attr('width', width)
      .attr('height', height);

    const x = d3.scaleBand()
      .domain(data.map(d => d.name))
      .range([margin.left, width - margin.right])
      .padding(0.2);

    const y = d3.scaleLinear()
      .domain([0, d3.max(data, d => d.value) as number])
      .nice()
      .range([height - margin.bottom, margin.top]);

    // 🎯 Step 1: Create a tooltip div (Make sure it is correctly added!)
    const tooltip = d3.select('body') // Append to body to avoid clipping
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

    // 🎯 Step 2: Draw bars and add hover events
    svg.append('g')
      .selectAll('rect')
      .data(data)
      .enter().append('rect')
      .attr('x', d => x(d.name)!)
      .attr('y', d => y(d.value))
      .attr('height', d => height - margin.bottom - y(d.value))
      .attr('width', x.bandwidth())
      .attr('fill', 'steelblue')
      .on('mouseover', function (event, d) {
        d3.select(this).attr('opacity', 1); // Highlight slice on hover
        d3.select(this).attr('fill', 'orange'); // Highlight bar on hover
        tooltip
          .style('display', 'block')
          .html(`<strong>${d.name}</strong>: ${d.value}`); // Set text
      })
      .on('mousemove', function (event) {
        tooltip
          .style('left', `${event.pageX + 10}px`)
          .style('top', `${event.pageY - 20}px`);
      })
      .on('mouseout', function () {
        d3.select(this).attr('fill', 'steelblue'); // Reset color
        tooltip.style('display', 'none');
      });

    // 🎯 Step 3: Add X and Y Axes
    svg.append('g')
      .attr('transform', `translate(0,${height - margin.bottom})`)
      .call(d3.axisBottom(x));

    svg.append('g')
      .attr('transform', `translate(${margin.left},0)`)
      .call(d3.axisLeft(y));
  }

}



