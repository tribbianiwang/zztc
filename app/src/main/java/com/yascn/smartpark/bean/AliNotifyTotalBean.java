package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/11/14.
 */

public class AliNotifyTotalBean {


    private List<OutputsBean> outputs;

    public List<OutputsBean> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<OutputsBean> outputs) {
        this.outputs = outputs;
    }

    public static class OutputsBean {
        /**
         * outputLabel : ocr_vehicle_license
         * outputMulti : {}
         * outputValue : {"dataType":50,"dataValue":"{                 \"config_str\": \"\",               #配置字符串信息                 \"owner\": \"张三\",                #所有人名称                 \"plate_num\": \"沪A0M084\",        #车牌号码                 \"model\":\"奥德赛HG7420\",         #品牌型号                 \"vin\" : \"LSVFF66R8C2116280\",    #车辆识别代号                 \"use_character\" : \"非营运\",     #使用性质                 \"issue_date\":\"20121127\"，      #发证日期                 \"vehicle_type\" : \"小型轿车\",    #车辆类型                 \"engine_num\" : \"416098\",        #发动机号码                 \"register_date\": \"20121127\",    #注册日期                 \"request_id\": \"84701974fb983158_20160526100112\",               #请求对应的唯一表示                 \"success\": true                 #识别成功与否 true/false             }"}
         */

        private String outputLabel;
        private OutputMultiBean outputMulti;
        private OutputValueBean outputValue;

        public String getOutputLabel() {
            return outputLabel;
        }

        public void setOutputLabel(String outputLabel) {
            this.outputLabel = outputLabel;
        }

        public OutputMultiBean getOutputMulti() {
            return outputMulti;
        }

        public void setOutputMulti(OutputMultiBean outputMulti) {
            this.outputMulti = outputMulti;
        }

        public OutputValueBean getOutputValue() {
            return outputValue;
        }

        public void setOutputValue(OutputValueBean outputValue) {
            this.outputValue = outputValue;
        }

        public static class OutputMultiBean {
        }

        public static class OutputValueBean {
            /**
             * dataType : 50
             * dataValue : {                 "config_str": "",               #配置字符串信息                 "owner": "张三",                #所有人名称                 "plate_num": "沪A0M084",        #车牌号码                 "model":"奥德赛HG7420",         #品牌型号                 "vin" : "LSVFF66R8C2116280",    #车辆识别代号                 "use_character" : "非营运",     #使用性质                 "issue_date":"20121127"，      #发证日期                 "vehicle_type" : "小型轿车",    #车辆类型                 "engine_num" : "416098",        #发动机号码                 "register_date": "20121127",    #注册日期                 "request_id": "84701974fb983158_20160526100112",               #请求对应的唯一表示                 "success": true                 #识别成功与否 true/false             }
             */

            private int dataType;
            private String dataValue;

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public String getDataValue() {
                return dataValue;
            }

            public void setDataValue(String dataValue) {
                this.dataValue = dataValue;
            }
        }
    }
}
