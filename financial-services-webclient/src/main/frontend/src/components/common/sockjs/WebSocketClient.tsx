import SockJsClient from 'react-stomp';
import Client from "@stomp/stompjs"
const SOCKET_URL = 'http://localhost:8080/market-data-stream';

export const WebSocketClient: React.FC = () => {
    return <SockJsClient
        url={SOCKET_URL}
        topics={['/topic/trade']}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={msg => onMessageReceived(msg)}
        debug={false}
    />
}

let onConnected = () => {
    console.log("Connected!!")
}

let onMessageReceived = (msg) => {
    console.log(msg)
}


export default WebSocketClient;