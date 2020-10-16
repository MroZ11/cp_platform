/**
 * Created by cloud on 2019/6/18.
 * 公用工具
 *
 */
var atutils = {

    /**
     *
     * @param url  路径
     * @param json json参数
     * @param heards 头
     * @param needMessage 是否需要提示消息 默认True
     * @param fnSuccess 成功操作
     * @param fnFail 失败操作
     */
    postJson: function (url, json, heards, needMessage = true, fnSuccess, fnFail) {
        var heards = heards || {};
        heards[csrfHeaderName] = csrfToken;
        //到后台去取list
        axios.post(url, json, {
            headers: heards
        }).then(function (response) {
            var data = response.data;
            if (needMessage) {
                var type = data.succeed ? 'success' : 'error';
                ELEMENT.Message({
                    type: type,
                    message: data.describe
                });
            }
            if (fnSuccess && data.succeed) {
                fnSuccess(data)
            }
            if (fnFail && !data.succeed) {
                fnFail(data)
            }
        }).catch(function (error) {
            console.log(error);
            if (fnFail) {
                fnFail(error)
            }
        });

    },

    postXform: function (url, json, heards, needMessage = true, fnSuccess, fnFail) {
        var heards = heards || {};
        heards[csrfHeaderName] = csrfToken;
        //到后台去取list
        axios.post(url, Qs.stringify(json), {
            headers: heards
        }).then(function (response) {
            var data = response.data;
            if (needMessage) {
                var type = data.succeed ? 'success' : 'error';
                ELEMENT.Message({
                    type: type,
                    message: data.describe
                });
            }
            if (fnSuccess && data.succeed) {
                fnSuccess(data)
            }
            if (fnFail && !data.succeed) {
                fnFail(data)
            }
        }).catch(function (error) {
            console.log(error);
            if (fnFail) {
                fnFail(error)
            }
        });
    },


}




