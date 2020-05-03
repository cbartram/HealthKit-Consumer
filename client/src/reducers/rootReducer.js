import { combineReducers } from 'redux';
import healthMetricReducer  from "./healthMetricReducer";

export default combineReducers({
    health: healthMetricReducer
});