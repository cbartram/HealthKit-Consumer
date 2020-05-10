import { combineReducers } from 'redux';
import healthMetricReducer  from "./healthMetricReducer";
import authReducer from "./authReducer";

export default combineReducers({
    health: healthMetricReducer,
    auth: authReducer
});