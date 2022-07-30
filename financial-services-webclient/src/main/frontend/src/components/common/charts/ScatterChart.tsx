import React from 'react';
import { Scatter } from "react-chartjs-2"
import { Chart as ChartJS } from 'chart.js/auto'
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

const ScatterChart = ({ chartData }) => {
    return <Scatter data={chartData} />
}

export default ScatterChart;
