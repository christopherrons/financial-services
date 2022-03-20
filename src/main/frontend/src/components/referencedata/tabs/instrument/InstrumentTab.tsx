
import styled from 'styled-components';
import { Col, Row } from "../../../common/grids/Grids";
import InstrumentsColumn from "./InstumentsColumn";

const InstrumentTab = () => {
    return (
        <div className="instrument-tab">
            <Row>
                <Col size={1}>
                    <h1>Trading Pairs</h1>
                </Col>
                <Col size={1}>
                    <h1>Instruments</h1>
                    <InstrumentsColumn />
                </Col>
            </Row>
        </div>
    );
}

export default InstrumentTab;