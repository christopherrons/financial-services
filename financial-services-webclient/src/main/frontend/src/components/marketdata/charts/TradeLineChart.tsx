import LineChart from "../../common/charts/LineChart";
import React, { useEffect, useState } from 'react';

let da = [65, 59, 80, 81, 56, 55, 400]
const TradeLineChart: React.FC = () => {
    const [data, setData] = useState({
        labels: [1, 2, 3, 4, 5, 6, 7],
        datasets: [{
            label: 'My First Dataset',
            data: da,
        }]
    })

    useEffect(() => {
        setData({
            labels: [1, 2, 3, 4, 5, 6, 7],
            datasets: [{
                label: 'My First Dataset',
                data: da,
            }]
        })

    }, da);

    return (
        <div className='marketData'>
            <div className='martketDataChart'>
                <LineChart chartData={data} />
            </div>
        </div>
    );

}

export default TradeLineChart;