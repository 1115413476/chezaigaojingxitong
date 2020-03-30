# QDCloudControl

#### 项目介绍
#启迪云控

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 配置文件说明

##### 1. faultConfig.txt
    1.1 配置文件中的目前只能配置三个事件 顺序分别是 "道路障碍"，"道路打滑eventType":6"，"道路施工eventType":10"
    1.2 暂不支持配置事件的图片配置
    1.3 事件以#分隔[{content}]#[{content}]#[{content}]
    1.4 配置中的 longitude latitude timeStamp vehicleId 代码中已经参数化 请勿修改配置文件中的以上key对应Value的占位符 
    1.5 配置示例：
        [{
         "vehicleId": vehicleIdValue,
         "obstacleType": 0,
         "geoInfo": {
          "vendor": "mqtt上报障碍物感知AndroidClient",
          "position": {
           "longitude": longitudeValue,
           "latitude": latitudeValue,
           "altitude": 20.973882,
           "pdop_accuracy": 78.0,
           "hdop_accuracy": 76.3,
           "vdop_accuracy": 0.9
          },
          "roadId": 78,
          "affectedLanes": [{
           "positiveLanes": 343,
           "negativeLanes": 213
          }]
         },
         "size": {
          "width": 45.2,
          "depth": 23.1,
          "height": 33.2
         },
         "color": 24,
         "movable": false,
         "direction": {
          "e": 67.23,
          "n": 45.55,
          "u": 23.56
         },
         "velocity": 0.33,
         "dangerLevel": 1,
         "timestamp": timestampValue
        }]#[{"vehicleId":vehicleIdValue,"eventType":6,"geoInfo":{"vendor":"自动化MQTT上报路面异常事件AndroidCliennt","position":{"longitude":longitudeValue,"latitude":latitudeValue,"altitude":20.973882,"pdop_accuracy": 99.8,"hdop_accuracy": 98.3,"vdop_accuracy": 0.9},"roadId":34,"affectedLanes":[{"positiveLanes":343,"negativeLanes":213}]},"dangerLevel":1,"timestamp":timestampValue}]
        #[{"vehicleId":vehicleIdValue,"eventType":10,"geoInfo":{"vendor":"自动化MQTT上报路面异常事件AndroidClient","position":{"longitude":longitudeValue,"latitude":latitudeValue,"altitude":20.973882,"pdop_accuracy": 99.8,"hdop_accuracy": 98.3,"vdop_accuracy": 0.9},"roadId":34,"affectedLanes":[{"positiveLanes":343,"negativeLanes":213}]},"dangerLevel":1,"timestamp":timestampValue}]
##### 2. constraintConfig.txt 进入事件队列的条件参数
    2.1 如需修改配置文件请确保修改的内容符合正确的json格式否则无效 距离单位:米 时间单位:秒）
    2.2 roadSkid 道路打滑
    2.3 roadConstruction 道路施工
    2.4 roadObstructions 路面障碍物
    2.5 busLanes 公交车道
    2.6 distance 信号灯处理距离
    2.7 timeout_queue：事件在队列中的保存时间
    2.8 timeout_mesage：事件更新的超时时间 详见需求文档 "列表的移除规则"
    2.9 配置示例：
        {
          "alertEvent":{
            "distance":{
              "roadSkid":150,
              "roadConstruction":150,
              "roadObstructions":150,
              "busLanes":100
            }
          },
          "signalLamp":{
            "distance":200
          },
          "messageQueue":{
            "timeout_queue":60,
            "timeout_mesage":2
          }
        }
