import React, { useEffect, useState } from 'react';
import LineChart from '../components/common/charts/LineChart';
import MainNavbar from '../components/common/navbar/MainNavbar';
import TradeLineChart from '../components/marketdata/charts/TradeLineChart';
import WebSocketClient from '../components/common/sockjs/WebSocketClient'
import SockJS from "sockjs-client"
import io from "socket.io-client"
import Stomp from "stompjs"
import ScatterChart from '../components/common/charts/ScatterChart';


const url = "http://localhost:8080/market-data-stream";
const socket = new SockJS(url);
const stompClient = Stomp.over(socket);

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

export const MarketDataView: React.FC = () => {
    const orderData = [{}]
    const orderDataColor: String[] = []
    const [orderChartData, setOrderChartData] = useState({
        datasets: [{
            label: 'Order Data',
            data: orderData,
            backgroundColor: orderDataColor,
        }],
    })

    const tradeData = [{}]
    const tradeDataColor: String[] = []
    const [tradeChartData, setTradeChartData] = useState({
        datasets: [{
            label: 'Trade Data',
            data: tradeData,
            backgroundColor: tradeDataColor,
        }],
    })

    useEffect(() => {
        stompClient.connect({}, function (frame) {
            console.log(frame);
            stompClient.subscribe('/topic/trade', function (trade) {
                console.log(trade);
                const tradeJsonData = JSON.parse(trade.body).tradeDataStreamItems
                tradeJsonData.map(tradeJsonData => tradeData.push({ x: tradeJsonData.timeStampMs, y: tradeJsonData.price }))
                tradeJsonData.map(tradeJsonData => tradeDataColor.push(tradeJsonData.isBidSideAggressor ? 'rgb(38, 204, 23)' : 'rgb(0, 0, 0)'))
                setTradeChartData({
                    datasets: [{
                        label: 'Trade Data',
                        data: tradeData,
                        backgroundColor: tradeDataColor,
                    }]
                })
            });
            stompClient.subscribe('/topic/orderBook', function (order) {
                console.log(order);
                const orderJsonData = JSON.parse(order.body).orderDataStreamItems
                orderJsonData.map(orderJsonData => orderData.push({ x: orderJsonData.timeStampMs, y: orderJsonData.price }))
                orderJsonData.map(orderJsonData => orderDataColor.push(orderJsonData.orderTypeEnum == "BUY" ? 'rgb(255, 99, 138)' : 'rgb(36, 71, 228)'))
                setOrderChartData({
                    datasets: [{
                        label: 'Order Data',
                        data: orderData,
                        backgroundColor: orderDataColor,
                    }]
                })
            });
        });
    }, []);

    return (
        <div className='marketData'>
            <div className='martketDataChart'>
                <ScatterChart chartData={tradeChartData} />
            </div>
        </div>
    );
}


export default MarketDataView;