<template>
  <div>
    <div id="content">
      <img src="../../public/static/image/学习路线.png" class="logo">
    </div>
    <div class="content-mainbox">
      <div class="row">
        <div class="col-md-12">
          <pagination ref="pagination" v-bind:list="listLine"></pagination>
        </div>
      </div>
      <br>
      <div class="listview">
        <div v-for="line in lines">
          <div class="aBox">
            <a v-on:click="toCourseLine(line)" style="cursor:pointer">
              <div class="imgcontainer">
                <img class="img-fluid" v-bind:src="line.image" id="line-img">
              </div>
            </a>
            <div class="lineitem">
              <h2>{{line.title}}</h2>
              <p>{{line.desc}}</p>
              <div class="pathinfo">
                <span>4门课</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Pagination from "../components/pagination";

  export default {
    components: {Pagination},
    name: 'line',
    data: function () {
      return {
        line: {},
        lines: [],
      }
    },
    mounted() {
      let _this = this;
      _this.$refs.pagination.size = 4;
      _this.listLine(1);
    },
    methods: {
      /**
       * 查询学习路线列表
       */
      listLine(page) {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/line/list', {
          page: page,
          size: _this.$refs.pagination.size,
        }).then((response) => {
          let resp = response.data;
          if (resp.success) {
            _this.lines = resp.content.list;
            _this.$refs.pagination.render(page, resp.content.total);
          }
        }).catch((response) => {
          console.log("error：", response);
        })
      },

      /**
       * 点击【课程路线】
       */
      toCourseLine(line) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_LINE, line);
        _this.$router.push("/courseLine");
      },
    }
  }
</script>

<style>
  #line-img{
    height: 150px;
  }
  #content {
    width: 1152px;
    height: inherit;
    margin: 0 auto;
    position: relative;
  }
  .logo {
    width: 420px;
    height: 60px;
    padding: 5px 0;
    display: block;
  }
  .content-mainbox {
    width: 1152px;
    height: inherit;
    position: relative;
    min-height: 400px;
    margin: 32px auto 0;
    background: #fff;
    padding: 24px;
    box-sizing: border-box;
  }
  .listview {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }
  .listview .aBox {
    margin-bottom: 16px;
    margin-right: 16px;
    height: 155px;
    display: flex;
    flex-direction: row;
  }
  a:link, a:visited {
    color: #1c1f21;
  }
  .listview .imgcontainer {
    width: 140px;
    height: 140px;
    position: relative;
    border-radius: 8px;
    transition: .3s all linear;
  }
  .listview .lineitem {
    position: relative;
    border-bottom: 1px solid rgba(28,31,33,.1);
    width: 372px;
    overflow: hidden;
    margin-left: 24px;
  }
</style>