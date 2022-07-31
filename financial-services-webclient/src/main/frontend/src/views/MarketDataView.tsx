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

const addItemToList = (setter, added, limit) => {
    setter(existingItems => {
        const currentList = [added, ...existingItems]

        if (currentList.length > limit) {
            currentList.pop()
        }
        return currentList;
    })
}

export const MarketDataView: React.FC = () => {
    const tradeData = [{}]
    const tradeDataColor: String[] = []

    const [tradeInfoList, setTradeInfo] = useState([{
        price: 0,
        volume: 0,
        time: new Date(0).toTimeString(),
        aggressor: ""
    }])


    const [orderbookDataAsk, setOrderbookDataAsk] = useState([{
        price: 0,
        volume: 0,
        level: 0,
    }])

    const [orderbookDataBid, setOrderbookDataBid] = useState([{
        price: 0,
        volume: 0,
        level: 0,
    }])


    const orderData = [{}]
    const orderDataColor: String[] = []
    const [chartData, setChartData] = useState({
        datasets: [
            {
                label: 'Trade Data',
                data: tradeData,
                backgroundColor: tradeDataColor,
            },
            {
                label: 'Order Data',
                data: orderData,
                backgroundColor: orderDataColor,
            }],
    })



    useEffect(() => {
        stompClient.connect({}, function (frame) {
            console.log(frame);
            stompClient.subscribe('/topic/trade', function (trade) {
                console.log(trade);
                const tradeJsonData = JSON.parse(trade.body).tradeDataStreamItems
                tradeJsonData.map(tradeJsonData => {
                    tradeData.push({ x: new Date(tradeJsonData.timeStampMs), y: tradeJsonData.price })
                    tradeDataColor.push(tradeJsonData.isBidSideAggressor ? 'rgb(38, 204, 23)' : 'rgb(0, 0, 0)')
                    addItemToList(setTradeInfo, {
                        price: tradeJsonData.price,
                        volume: tradeJsonData.volume,
                        time: new Date(tradeJsonData.timeStampMs).toTimeString(),
                        aggressor: tradeJsonData.isBidSideAggressor ? "BUY" : "SELL",
                    }, 10)
                })
                setChartData({
                    datasets: [{
                        label: 'Trade Data',
                        data: tradeData,
                        backgroundColor: tradeDataColor,
                    },
                    {
                        label: 'Order Data',
                        data: orderData,
                        backgroundColor: orderDataColor,
                    }]
                })
            });
            stompClient.subscribe('/topic/orderBook', function (orderbookSnapshot) {
                console.log(orderbookSnapshot);
                const orderbookJsonData = JSON.parse(orderbookSnapshot.body);
                const askLevelData = orderbookJsonData.askPriceLevelsData;
                askLevelData.reverse().map(askLevelData => {
                    addItemToList(setOrderbookDataAsk, {
                        price: askLevelData.price,
                        volume: askLevelData.volume,
                        level: askLevelData.priceLevel
                    }, 5)
                })
                orderData.push({ x: new Date(), y: orderbookJsonData.bestAsk })
                orderDataColor.push('rgb(40, 37, 206)')
                const bidLevelData = orderbookJsonData.bidPriceLevelsData;
                bidLevelData.reverse().map(bidLevelData => {
                    addItemToList(setOrderbookDataBid, {
                        price: bidLevelData.price,
                        volume: bidLevelData.volume,
                        level: bidLevelData.priceLevel
                    }, 5)
                })
                orderData.push({ x: new Date(), y: orderbookJsonData.bestBid })
                orderDataColor.push('rgb(241, 50, 50)')
                setChartData({
                    datasets: [
                        {
                            label: 'Trade Data',
                            data: tradeData,
                            backgroundColor: tradeDataColor,
                        },
                        {
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
                <ScatterChart chartData={chartData} />
            </div>
            <div className='tradeEvents'>
                <h3>Trades</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Trade Time</th>
                            <th>Price</th>
                            <th>Volume</th>
                            <th>Aggressor Side</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tradeInfoList.map((tradeInfo) => (
                            <tr>
                                <td>{tradeInfo.time}</td>
                                <td>{tradeInfo.price}</td>
                                <td>{tradeInfo.volume}</td>
                                <td>{tradeInfo.aggressor}</td>
                            </tr>
                        ))}

                    </tbody>
                </table>
            </div>
            <div className='orderbookBid'>
                <h3>Bid</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Level</th>
                            <th>Price</th>
                            <th>Volume</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orderbookDataBid.map((orderbookDataBid) => (
                            <tr>
                                <td>{orderbookDataBid.level}</td>
                                <td>{orderbookDataBid.price}</td>
                                <td>{orderbookDataBid.volume}</td>
                            </tr>
                        ))}

                    </tbody>
                </table>
            </div>
            <div className='orderbookAsk'>
                <h3>Ask</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Level</th>
                            <th>Price</th>
                            <th>Volume</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orderbookDataAsk.map((orderbookDataAsk) => (
                            <tr>
                                <td>{orderbookDataAsk.level}</td>
                                <td>{orderbookDataAsk.price}</td>
                                <td>{orderbookDataAsk.volume}</td>
                            </tr>
                        ))}

                    </tbody>
                </table>
            </div>
        </div>
    );
}


export default MarketDataView;