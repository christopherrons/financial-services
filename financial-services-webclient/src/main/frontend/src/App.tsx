import './css/App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React, { Suspense } from 'react';
import MarketDataView from './views/MarketDataView';
import MainNavbar from './components/common/navbar/MainNavbar';


function App() {
  const ReferenceDataView = React.lazy(() => import('./views/ReferenceDataView'));
  return (
    <Router>
      <div className='App'>
        <MainNavbar />
        <div className='content'>
        <Suspense fallback={<div>Loading...</div>}>
          <Routes>
            <Route path='/' element={<MarketDataView />} />
            <Route path='/marketdata' element={<MarketDataView />} />
            <Route path='/surveillance' element={<MarketDataView />} />
            <Route path='/pricing' element={<MarketDataView />} />
            <Route path='/risk' element={<MarketDataView />} />
            <Route path='/referencedata' element={<ReferenceDataView />} />
          </Routes>
          </Suspense>
        </div>
      </div>
    </Router>
  );
}

export default App;
