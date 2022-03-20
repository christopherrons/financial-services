import ReferenceDataTabs from '../components/referencedata/tabs/ReferenceDataTabs';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Suspense } from 'react';


const ReferenceDataView = () => {
    return (
        <div className="reference-data-view">
            <ReferenceDataTabs />
        </div>
    );
}


export default ReferenceDataView;