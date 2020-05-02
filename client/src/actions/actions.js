

/**
 * Updates the name of the video the user is currently
 * watching
 * @param video Object the video object to make active
 * @returns {Function}
 */
export const getItems = (video) => dispatch => {
    dispatch({
        type: constants.UPDATE_ACTIVE_VIDEO,
        payload: video
    });
};

/**
 * Sends a user's current video information (such as duration completed, started watching etc.) to
 * the backend for storage. This also updates redux with the latest values.
 * @returns {Function}
 */
export const ping = (payload) => async (dispatch, getState) => {
    await post(payload, constants.API_PING_VIDEO, constants.PING_REQUEST, constants.PING_RESPONSE_SUCCESS, constants.PING_RESPONSE_FAILURE, dispatch, getState);
};