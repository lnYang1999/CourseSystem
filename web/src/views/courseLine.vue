<template>
  <div class="pathmain">
    <div class="banner">
      <div id="content">
        <img src="../../public/static/image/学习路线.png" class="logo">
        <h2>{{ line.title }}</h2>
        <p>{{ line.desc }}</p>
      </div>
    </div>
    <div class="routeview">
      <h2>
        <b>路线详情：</b>
        <span>{{ count }}门课</span>
      </h2>
      <div v-for="courseLine in courseLines">
        <div class="coursestep" v-for="course in courses.filter(c=>{return c.id===courseLine.courseId})">
          <div class="coursetitle">
            <span class="updateicon"></span>
            <span class="txt">{{ course.name }}</span>
          </div>
          <div class="courseitem">
            <div class="item-desc">
              <h3>课程简介</h3>
              <p class="mb24">
                {{ course.summary }}
              </p>
              <div class="btn-group">
                <router-link v-bind:to="'/detail?id=' + course.id" class="btn btn-outline-secondary">课程详情</router-link>
              </div>
            </div>
            <div class="item-course">
              <div class="imgcontainer">
                <img class="img-fluid" v-bind:src="course.image">
              </div>
              <div class="course-info">
                <h6>{{ course.name }}</h6>
                <div class="study-info">
                  <div class="course-level">
                    <span>{{COURSE_LEVEL | optionKV(course.level)}}</span>
                  </div>
                  <div class="course-price">
                    <span><i class="fa fa-yen" aria-hidden="true"></i>&nbsp;{{ course.price }}元</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "courseLine",
    data: function () {
      return {
        courseLine: {},
        courseLines: [],
        line: {},
        course: {},
        courses: [],
        COURSE_LEVEL: COURSE_LEVEL,
        count: "",
      }
    },
    mounted: function () {
      let _this = this;
      let line = SessionStorage.get(SESSION_KEY_LINE) || {};
      if (Tool.isEmpty(line)) {
        _this.$router.push("/welcome");
      }
      _this.line = line;
      _this.allCourse();
      _this.list(1);
    },
    methods: {
      /**
       * 列表查询
       */
      list(page) {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/courseLine/list', {
          page: page,
          lineId: _this.line.id
        }).then((response)=>{
          let resp = response.data;
          _this.count = resp.content.total;
          _this.courseLines = resp.content.list;
        })
      },

      allCourse() {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/course/all').then((response)=>{
          let resp = response.data;
          _this.courses = resp.content;
        })
      },
    }
  }
</script>

<style>
  .pathmain {
    position: relative;
     padding-bottom: 60px;
  }
  .banner {
    height: 220px;
    width: 100%;
    position: relative;
    background-color: #54acdb;
  }
  #content {
    width: 1152px;
    height: inherit;
    margin: 0 auto;
    position: relative;
  }
  .banner h2 {
    margin: 19px 0 12px 0;
    text-align: center;
    font-size: 30px;
    color: #fff;
    font-weight: 700;
    line-height: 32px;
    text-shadow: 0 2px 4px rgb(28 31 33 / 60%);
  }
  .banner p {
    text-align: center;
    font-size: 16px;
    line-height: 22px;
    color: #fff;
    text-shadow: 0 2px 4px rgb(28 31 33 / 60%);
  }
  .routeview {
    padding-top: 77px;
    width: 1152px;
    justify-content: space-between;
    margin: 0 auto;
    position: center;
    padding: 28px 32px;
    box-sizing: border-box;
    background: #fff;
    box-shadow: 0 4px 8px 0 rgb(0 0 0 / 5%);
  }
  .routeview .coursestep {
    border-left: 2px solid #f3f5f6;
    margin-left: 15px;
    padding-bottom: 40px;
    clear: both;
  }
  .routeview .coursestep .coursetitle {
     position: relative;
     left: -16px;
     line-height: 32px;
  }
  .routeview .coursestep .coursetitle .updateicon {
    position: absolute;
    z-index: 2;
    background: url(/static/image/updateicon.png);
    background-repeat: no-repeat;
    background-size: cover;
    width: 32px;
    height: 32px;
  }
  .routeview .coursestep .coursetitle .txt {
    margin-left: 48px;
    font-weight: 700;
    font-size: 18px;
    color: #07111b;
    text-align: left;
  }
  .routeview .coursestep .courseitem {
    margin-top: 24px;
    background: url(/static/image/coursebg.png);
    background-repeat: no-repeat;
    background-position: top center;
    background-size: cover;
    width: auto;
    height: 230px;
    border-radius: 12px;
    padding: 30px 32px;
  }
  .routeview .coursestep .courseitem .imgcontainer {
    width: 140px;
    height: 100px;
    position: relative;
    border-radius: 8px;
    transition: .3s all linear;
  }
  .routeview .coursestep .courseitem .item-desc {
    float: left;
    color: #383d42;
    text-align: left;
    width: 650px;
  }
  .routeview .coursestep .courseitem .item-course {
    width: 270px;
    height: auto;
    float: right;
    border-radius: 8px;
    background: #fff;
    transition: .3s all linear;
    position: relative;
    margin-left: 44px;
  }
  .routeview .coursestep .courseitem .item-desc h3 {
    font-weight: 700;
    font-size: 22px;
    line-height: 16px;
    margin-bottom: 15px;
  }
  .routeview .coursestep .courseitem .item-desc p {
    font-size: 16px;
    line-height: 26px;
  }
  .mb24 {
    margin-bottom: 24px;
    height: 100px;
  }
  .routeview .coursestep .courseitem .item-course {
    width: 270px;
    height: auto;
    float: right;
    border-radius: 8px;
    background: #fff;
    transition: .3s all linear;
    position: relative;
    margin-left: 44px;
  }
  .routeview .coursestep .courseitem .item-course a {
    display: block;
  }
  .routeview .coursestep .courseitem .item-course .course-info {
    padding: 21px 24px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
  }
  .routeview .coursestep .courseitem .item-course .course-info .study-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    line-height: 18px;
    font-size: 12px;
  }
  .routeview .coursestep .courseitem .item-course .course-info .study-info .course-level {
    color: #9199a1;
  }
  .routeview .coursestep .courseitem .item-course .course-info .study-info .course-price {
    color: #545c63;
  }
</style>