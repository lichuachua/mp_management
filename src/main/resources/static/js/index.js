/*
 * @Author: johnwang
 * @LastAuthor: Do not edit
 * @Github: https://github.com/tyutjohn
 * @since: 2019-04-11 09:03:00
 * @lastTime: 2019-04-23 18:58:19
 */
//设置token
var token = document.cookie.split(";")[0];
document.querySelector('#token').setAttribute('value', token);
console.log(token);
//判断是否登陆
var login_token=document.querySelector('#token').value;
if(login_token==''){
    window.location.href="login.html"
}

//侧边栏导航
var app=new Vue({
    el:'#app',
    data:{},
    mounted(){
      this.home();
    },
    methods: {
        //加载主页
        home:function(){
            $.ajax({
                url: '../model/home.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        //ajax加载主页
        go_home:function(){
            $.ajax({
                url: '../model/home.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        bulletin_first: function () {
            $.ajax({
                url: '../model/bulletin_first.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
            //alert("success")
        },
        bulletin_second: function () {
            $.ajax({
                url: '../model/bulletin_second.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        bulletin_third: function () {
            $.ajax({
                url: '../model/bulletin_third.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        school_first: function () {
            $.ajax({
                url: '../model/schoolroll_add_school.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        college_first:function(){
            $.ajax({
                url: '../model/schoolroll_add_college.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        school_second:function(){
            $.ajax({
                url: '../model/schoolroll_list_school.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        college_second:function(){
            $.ajax({
                url: '../model/schoolroll_list_college.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        article_first:function(){
            $.ajax({
                url: '../model/article_manage.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        article_second:function(){
            $.ajax({
                url: '../model/article_type.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        article_third:function(){
            $.ajax({
                url: '../model/article_comment.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        user_normal:function(){
            $.ajax({
                url: '../model/user_normal.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        user_disable:function(){
            $.ajax({
                url: '../model/user_disable.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        article_log:function(){
            $.ajax({
                url: '../model/article_log.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        comment_log:function(){
            $.ajax({
                url: '../model/comment_log.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        adminset:function(){
            $.ajax({
                url: '../model/adminset.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        adminuserset:function(){
            $.ajax({
                url: '../model/adminuserset.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        team_first:function(){
            $.ajax({
                url: '../model/team_manage.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        team_second:function(){
            $.ajax({
                url: '../model/team_type.html',
                type: 'get',
                success: function (res) {
                    $('#model').html($(res));
                },
                error: function (res) {
                    console.log(res)
                }
            });
        },
        //退出登陆
        out:function(){
            let appendForm = new FormData();
            let token=document.querySelector('#token').value;
            appendForm.append('accessToken',token);
            this.$http.put('http://127.0.0.1:8081/admin/logout',appendForm,{
                'Content-Type': 'Multipart/form-data'
            }).then(
                function(res){
                        new $.zui.Messager('正在退出',{
                            type:'success',
                            placement:'center',
                            icon:'icon-ok-sign'
                        }).show();
                        window.location.href='login.html';
                        console.log(res)
                },function(res){
                        new $.zui.Messager('网络错误或找不到服务器',{
                            type:'danger',
                            placement:'center',
                            icon:'icon-exclamation-sign'
                        }).show();
                    console.log(res)
                }
            ).catch(function(reason){
                console.log(reason)
            })
        }
    }
});
