<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/line" class="pink"> {{line.title}} </router-link>
    </h4>
    <hr>
    <p>
      <router-link to="/business/line" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回路线
      </router-link>
      &nbsp;
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>课程id</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="courseLine in courseLines">
              <td>{{courseLine.id}}</td>
              <td>{{courseLine.courseId}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(courseLine)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-on:click="del(courseLine.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">课程id</label>
                <div class="col-sm-10">
                  <input v-model="courseLine.courseId" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">路线</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{line.title}}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "business-courseLine",
    data: function() {
      return {
        courseLine: {},
        courseLines: [],
        line: {},
      }
    },
    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      let line = SessionStorage.get(SESSION_KEY_LINE) || {};
      if (Tool.isEmpty(line)) {
        _this.$router.push("/welcome");
      }
      _this.line = line;
      _this.list(1);
      // sidebar激活样式方法一
      this.$parent.activeSidebar("business-line-sidebar");

    },
    methods: {
      /**
       * 点击【新增】
       */
      add() {
        let _this = this;
        _this.courseLine = {};
        $("#form-modal").modal("show");
      },

      /**
       * 点击【编辑】
       */
      edit(courseLine) {
        let _this = this;
        _this.courseLine = $.extend({}, courseLine);
        $("#form-modal").modal("show");
      },

      /**
       * 列表查询
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/courseLine/list', {
          page: page,
          size: _this.$refs.pagination.size,
          lineId: _this.line.id
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.courseLines = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {
        let _this = this;

        // 保存校验
        if (1 != 1
                || !Validator.length(_this.courseLine.lineId, "学习路线Id", 1, 8)
                || !Validator.length(_this.courseLine.courseId, "课程ID", 1, 8)
        ) {
          return;
        }
        _this.courseLine.lineId = _this.line.id;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/courseLine/save', _this.courseLine).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },

      /**
       * 点击【删除】
       */
      del(id) {
        let _this = this;
        Confirm.show("删除课程路线后不可恢复，确认删除？", function () {
          Loading.show();
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/courseLine/delete/' + id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      }
    }
  }
</script>