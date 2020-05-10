import {
    OAUTH_TOKEN_FAILURE,
    OAUTH_TOKEN_SUCCESS,
    REQUEST_OAUTH_TOKEN
} from "../constants";

export default (state = {}, action) => {
    switch (action.type) {
        case REQUEST_OAUTH_TOKEN:
            return {
                ...state,
                ...action.payload
            };
        case OAUTH_TOKEN_SUCCESS:
            return {
                ...state,
                ...action.payload,
            };
        case OAUTH_TOKEN_FAILURE:
            return {
                ...state,
            };
        default:
            return {
                ...state,
            }
    }
}