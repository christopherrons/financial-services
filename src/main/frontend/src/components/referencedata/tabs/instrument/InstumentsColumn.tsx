import AvailableInstrumentsByTypeRequest from "../../request/AvailableInstrumentsByTypeRequest";
import InstrumentTypeDropdown from "./InstrumentTypeDropdown";

const IntrumentTypeColumn = () => {
    return (
        <>
            <div className="instument-type-column">
                <InstrumentTypeDropdown />
                <AvailableInstrumentsByTypeRequest instrumentType={InstrumentTypeDropdown}/>
            </div>
        </>
    );
}

export default IntrumentTypeColumn;