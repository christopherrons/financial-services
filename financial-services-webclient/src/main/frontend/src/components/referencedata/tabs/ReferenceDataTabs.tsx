import React from "react";
import { Tabs, Tab, AppBar } from "@material-ui/core"
import UserTab from "./UserTab"
import PortfolioTab from "./PortfolioTab"
import InstrumentTab from "./instrument/InstrumentTab";

const ReferenceDataTabs = () => {

    const style = {
        "padding": "0.2rem calc((75vw - 1000px) / 2)",
        "backgroundColor": "#ccc"
    }

    const [selectTab, setSelectTab] = React.useState(0);

    const handleChange = (event, newValue) => {
        setSelectTab(newValue);
    };

    return (
        <>
            <Tabs value={selectTab} onChange={handleChange} style={style}>
                <Tab label="User" />
                <Tab label="Portfolio" />
                <Tab label="Instruments" />
                <Tab label="Trading Pairs" />
            </Tabs>
            {selectTab === 0 && <UserTab />}
            {selectTab === 1 && <PortfolioTab />}
            {selectTab === 2 && <InstrumentTab />}
        </>
    );
};

export default ReferenceDataTabs;
