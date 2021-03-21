<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img
                                src="../assets/img/img.jpg"
                                class="user-avator"
                                alt
                        />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{ name }}</div>
                            <div>{{ role }}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span>2019-11-01</span>
                    </div>
                    <div class="user-info-list">
                        上次登录地点：
                        <span>东莞</span>
                    </div>
                </el-card>
                <!--<el-card shadow="hover" style="height:252px;">-->
                <!--<template #header>-->
                <!--<div class="clearfix">-->
                <!--<span>语言详情</span>-->
                <!--</div>-->
                <!--</template>-->
                <!--Vue-->
                <!--<el-progress-->
                <!--:percentage="71.3"-->
                <!--color="#42b983"-->
                <!--&gt;</el-progress-->
                <!--&gt;JavaScript-->
                <!--<el-progress-->
                <!--:percentage="24.1"-->
                <!--color="#f1e05a"-->
                <!--&gt;</el-progress-->
                <!--&gt;CSS <el-progress :percentage="13.7"></el-progress>HTML-->
                <!--<el-progress-->
                <!--:percentage="5.9"-->
                <!--color="#f56c6c"-->
                <!--&gt;</el-progress>-->
                <!--</el-card>-->
            </el-col>
            <el-col :span="16">
                <!--<el-row :gutter="20" class="mgb20">-->
                    <!--<el-col :span="8">-->
                        <!--<el-card-->
                                <!--shadow="hover"-->
                                <!--:body-style="{ padding: '0px' }"-->
                        <!--&gt;-->
                            <!--<div class="grid-content grid-con-1">-->
                                <!--<i class="el-icon-user-solid grid-con-icon"></i>-->
                                <!--<div class="grid-cont-right">-->
                                    <!--<div class="grid-num">1234</div>-->
                                    <!--<div>用户访问量</div>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</el-card>-->
                    <!--</el-col>-->
                    <!--<el-col :span="8">-->
                        <!--<el-card-->
                                <!--shadow="hover"-->
                                <!--:body-style="{ padding: '0px' }"-->
                        <!--&gt;-->
                            <!--<div class="grid-content grid-con-2">-->
                                <!--<i-->
                                        <!--class="el-icon-message-solid grid-con-icon"-->
                                <!--&gt;</i>-->
                                <!--<div class="grid-cont-right">-->
                                    <!--<div class="grid-num">321</div>-->
                                    <!--<div>系统消息</div>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</el-card>-->
                    <!--</el-col>-->
                    <!--<el-col :span="8">-->
                        <!--<el-card-->
                                <!--shadow="hover"-->
                                <!--:body-style="{ padding: '0px' }"-->
                        <!--&gt;-->
                            <!--<div class="grid-content grid-con-3">-->
                                <!--<i class="el-icon-s-goods grid-con-icon"></i>-->
                                <!--<div class="grid-cont-right">-->
                                    <!--<div class="grid-num">5000</div>-->
                                    <!--<div>数量</div>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</el-card>-->
                    <!--</el-col>-->
                <!--</el-row>-->
                <el-card shadow="hover" style="height:1800px;">
                    <template #header>
                        <div class="clearfix">
                            <span style="color: #20a0ff ;font-weight: 700">Banner图片操作</span>
                            <el-button
                                    style="float: right; padding: 3px 0"
                                    type="text"
                            >添加Banner图片
                            </el-button
                            >
                        </div>
                    </template>
                    <!--图片开始。。。。。-->
                    <el-row>
                        <el-col :span="8" v-for="(item, index) in bannerList" :key="item" :offset="index >= 0 ? 2 : 0" style="border: #20a0ff 1px solid;margin-bottom: 20px;border-radius: 5px">
                            <el-card :body-style="{ padding: '0px'}">
                                <img :src=item.url
                                     class="image">
                                <div style="padding: 14px;">
                                    <div class="bottom clearfix">
                                        <time class="time">{{ currentDate }}</time>
                                        <el-button type="text" @click="delBanner(item.id)" class="button">删除</el-button>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>

                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {

        name: "dashboard",
        data() {
            return {
                name: localStorage.getItem("ms_username"),
                currentDate: new Date(),
                bannerList:[
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":1},
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":2},
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":3},
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":4},
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":5},
                    {"url": 'https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/banner/banner%402x.png',"id":6}]
            };
        },
        components: {},
        computed: {
            role() {
                return this.name === "admin" ? "超级管理员" : "普通用户";
            },
        },

        methods: {
            delBanner(e){
                axios.get(
                    "https://applet.konicaminolta-bcn.cn/wx/getBanner",
                    {}
                ).then((success)=>{
                    console.log(JSON.stringify(success))
                }).catch((error)=>{
                    console.log(error)
                })
                console.log("111")
                console.log(e)
            }
        },
    };
</script>

<style scoped>
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }



</style>
