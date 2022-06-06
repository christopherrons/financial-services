import React from "react";
import { DropdownWrapper, StyledButton, StyledLabel, StyledOption, StyledSelect } from "../../../common/dropdown/DropdownWrapper";
import availableInstrumentTypesRequest from "../../request/AvailableInstrumentTypesRequest";


function Dropdown(props) {
    return (
        <DropdownWrapper action={props.action}>
            <StyledLabel htmlFor="services">
                {props.formLabel}
            </StyledLabel>
            <StyledSelect id="services" name="services">
                {props.children}
            </StyledSelect>
            <StyledButton type="submit" value={props.buttonText} />
        </DropdownWrapper>
    );
}

function Option(props) {
    return (
        <>
            <StyledOption selected={props.selected}>
                {props.value}
            </StyledOption>
        </>
    );
}

export class InstrumentTypeDropdown extends React.Component<{}, { instrumentTypes: Array<String>, chosenValue: String }>  {
    constructor(props) {
        super(props)
        this.state = {
            instrumentTypes: [],
            chosenValue: 'bitstamp',
        }
    }

    componentDidMount() {
        availableInstrumentTypesRequest()
            .then((data) => {
                this.setState({ instrumentTypes: data })
            })
    }

    onDropdownChange = (e) => {
        console.log(this.state.chosenValue);
        this.setState({ chosenValue: e.target.value })
        console.log(this.state.chosenValue);
    }

    render() {
        return (
            <div className="instrument-type-dropdown">
                <Dropdown
                    formLabel="Select Instrument Type"
                    buttonText="Get Instruments"
                    onDropdownChange={this.onDropdownChange}
                >
                    {
                        this.state.instrumentTypes.length ?
                            this.state.instrumentTypes.map(instrumentType => <Option key={instrumentType} value={instrumentType} />) :
                            null
                    }
                </Dropdown>
            </div>
        )
    }
}

export default InstrumentTypeDropdown;