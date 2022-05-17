
function RemoteFunctions() { }
RemoteFunctions._path = '/javabb/dwr';

RemoteFunctions.deleteFile = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'deleteFile', p0, callback);
}

RemoteFunctions.setPostTransaction = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setPostTransaction', p0, callback);
}

RemoteFunctions.setTopicTransaction = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setTopicTransaction', p0, callback);
}

RemoteFunctions.addFavoriteTopic = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'addFavoriteTopic', p0, callback);
}

RemoteFunctions.deleteFavoriteTopic = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'deleteFavoriteTopic', p0, callback);
}

RemoteFunctions.addWatchTopic = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'addWatchTopic', p0, callback);
}

RemoteFunctions.deleteWatchTopic = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'deleteWatchTopic', p0, callback);
}

RemoteFunctions.spyTemplate = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'spyTemplate', callback);
}

RemoteFunctions.getSessionAttribute = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getSessionAttribute', p0, callback);
}

RemoteFunctions.getPage = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getPage', callback);
}

RemoteFunctions.htmlEscape = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'htmlEscape', p0, callback);
}

RemoteFunctions.getApplication = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getApplication', callback);
}

RemoteFunctions.setOnline = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setOnline', p0, callback);
}

RemoteFunctions.setJbbConfig = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setJbbConfig', p0, callback);
}

RemoteFunctions.getJbbConfig = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getJbbConfig', callback);
}

RemoteFunctions.getTimer = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getTimer', callback);
}

RemoteFunctions.getOnline = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getOnline', callback);
}

RemoteFunctions.getUrl = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getUrl', callback);
}

RemoteFunctions.setUrl = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setUrl', p0, callback);
}

RemoteFunctions.setPage = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setPage', p0, callback);
}

RemoteFunctions.getPages = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getPages', callback);
}

RemoteFunctions.getLastPage = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getLastPage', callback);
}

RemoteFunctions.getTemp = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getTemp', callback);
}

RemoteFunctions.setTemp = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setTemp', p0, callback);
}

RemoteFunctions.setSessionAttribute = function(p0, p1, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setSessionAttribute', p0, p1, callback);
}

RemoteFunctions.removeSessionAttribute = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'removeSessionAttribute', p0, callback);
}

RemoteFunctions.getPagedResult = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getPagedResult', callback);
}

RemoteFunctions.setPagedResult = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setPagedResult', p0, callback);
}

RemoteFunctions.isTopicRead = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'isTopicRead', p0, callback);
}

RemoteFunctions.isForumRead = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'isForumRead', p0, callback);
}

RemoteFunctions.getNmbUnreadsInCat = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getNmbUnreadsInCat', p0, callback);
}

RemoteFunctions.checkCaptcha = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'checkCaptcha', callback);
}

RemoteFunctions.getCaptchafield = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getCaptchafield', callback);
}

RemoteFunctions.getUserLogged = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getUserLogged', callback);
}

RemoteFunctions.setCaptchafield = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setCaptchafield', p0, callback);
}

RemoteFunctions.getTopicViews = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getTopicViews', callback);
}

RemoteFunctions.execute = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'execute', callback);
}

RemoteFunctions.validate = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'validate', callback);
}

RemoteFunctions.getLocale = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getLocale', callback);
}

RemoteFunctions.getErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getErrors', callback);
}

RemoteFunctions.getText = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getText', p0, callback);
}

RemoteFunctions.getText = function(p0, p1, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getText', p0, p1, callback);
}

RemoteFunctions.getText = function(p0, p1, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getText', p0, p1, callback);
}

RemoteFunctions.getText = function(p0, p1, p2, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getText', p0, p1, p2, callback);
}

RemoteFunctions.getText = function(p0, p1, p2, p3, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getText', p0, p1, p2, p3, callback);
}

RemoteFunctions.setActionErrors = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setActionErrors', p0, callback);
}

RemoteFunctions.getActionErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getActionErrors', callback);
}

RemoteFunctions.setActionMessages = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setActionMessages', p0, callback);
}

RemoteFunctions.getActionMessages = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getActionMessages', callback);
}

RemoteFunctions.getErrorMessages = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getErrorMessages', callback);
}

RemoteFunctions.setFieldErrors = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'setFieldErrors', p0, callback);
}

RemoteFunctions.getFieldErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getFieldErrors', callback);
}

RemoteFunctions.getTexts = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getTexts', p0, callback);
}

RemoteFunctions.getTexts = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'getTexts', callback);
}

RemoteFunctions.addActionError = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'addActionError', p0, callback);
}

RemoteFunctions.addActionMessage = function(p0, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'addActionMessage', p0, callback);
}

RemoteFunctions.addFieldError = function(p0, p1, callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'addFieldError', p0, p1, callback);
}

RemoteFunctions.doDefault = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'doDefault', callback);
}

RemoteFunctions.hasActionErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'hasActionErrors', callback);
}

RemoteFunctions.hasActionMessages = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'hasActionMessages', callback);
}

RemoteFunctions.hasErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'hasErrors', callback);
}

RemoteFunctions.hasFieldErrors = function(callback) {
    DWREngine._execute(RemoteFunctions._path, 'RemoteFunctions', 'hasFieldErrors', callback);
}
