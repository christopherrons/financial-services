import styled from 'styled-components';

export const Grid = styled.div`
    
`;

export const Row = styled.div`
    display: flex;
    height: 500
`;

export const Col = styled.div`
    flex: ${(props) => props.size};
    color: ${(props) => props.color};
    border: 0.1px solid black;
    margin: 15px;
`;