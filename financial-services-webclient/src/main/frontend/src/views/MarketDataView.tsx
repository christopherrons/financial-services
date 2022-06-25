import React, { useEffect, useState } from 'react';
import LineChart from '../components/common/charts/LineChart';
import MainNavbar from '../components/common/navbar/MainNavbar';
import TradeLineChart from '../components/marketdata/charts/TradeLineChart';
import WebSocketClient from '../components/common/sockjs/WebSocketClient'

export const MarketDataView: React.FC = () => {
    const [data, setData] = useState({
        labels: [1, 2, 3, 4, 5, 6, 7],
        datasets: [{
            label: 'My First Dataset',
            data: [65, 59, 80, 81, 56, 55, 40],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(255, 205, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(201, 203, 207, 0.2)'
            ],
            borderColor: [
                'rgb(255, 99, 132)',
                'rgb(255, 159, 64)',
                'rgb(255, 205, 86)',
                'rgb(75, 192, 192)',
                'rgb(54, 162, 235)',
                'rgb(153, 102, 255)',
                'rgb(201, 203, 207)'
            ],
            borderWidth: 1
        }]
    })

    return (
        <div className='marketData'>
            <div className='martketDataChart'>
                <LineChart chartData={data} />
            </div>
            <div>
                <WebSocketClient />
            </div>
        </div>
    );
}


export default MarketDataView;