import React from 'react';
import { Line } from "react-chartjs-2"
import { Chart as ChartJS } from 'chart.js/auto'
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

const LineChart = ({ chartData }) => {
    return <Line data={chartData} />
}

export default LineChart;
