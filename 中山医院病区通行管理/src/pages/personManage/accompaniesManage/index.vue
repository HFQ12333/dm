<template>
  <div class="accompanies">
    <!-- 搜索区域 -->
    <div class="header">
      <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item>
          <el-button type="primary" size="mini">{{ ward_address }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.hospitalization_status"
            size="small"
            placeholder="请选择"
          >
            <el-option label="全部" value="2" />
            <el-option label="住院中" value="0" />
            <el-option label="已出院" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.state"
            size="small"
            placeholder="请选择陪护状态"
          >
            <el-option label="全部" value="" />
            <el-option label="有效" value="1" />
            <el-option label="无效" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.search"
            placeholder="请输入信息搜索"
            prefix-icon="el-icon-search"
            size="small"
          />
        </el-form-item>
        <div style="float: right">
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="toAddHandler"
              >登记</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-download"
              size="mini"
              @click="exportList"
              >导出</el-button
            >
          </el-form-item>
        </div>
      </el-form>
    </div>

    <!-- 表格 -->
    <el-table
      :data="accompany.results"
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'ascending' }"
      size="small"
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#606266',
      }"
      @sort-change="sortHandler"
    >
      <el-table-column
        prop="date"
        label="病员信息"
        align="center"
        sortable="custom"
        width="150"
        fixed
      >
        <template slot-scope="scope">
          <div v-if="scope.row.patient_info.bed_num" class="patient_table_1">
            <span style="width: 32px">{{
              scope.row.patient_info.bed_num
            }}</span>
            -
            <span class="simpleContent" style="width: 45px">{{
              scope.row.patient_info.name
            }}</span>
          </div>
          <div v-else class="patient_table_1">
            <span style="width: 32px">--</span> -
            <span class="simpleContent" style="width: 45px">{{
              scope.row.patient_info.name
            }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="陪护姓名"
        align="center"
        class="show_img"
        width="220"
      >
        <template slot-scope="scope">
          <div class="table_img_name" :title="scope.row.name">
            {{ scope.row.name }}
          </div>
          <el-popover placement="right" trigger="hover">
            <img :src="scope.row.face_data" />
            <img
              slot="reference"
              :src="scope.row.face_data"
              style="max-height: 50px; max-width: 150px"
            />
          </el-popover>
          <!-- <div class="show_img no_wrap">
            <div class="table_img">
              <img :src="scope.row.face_data" alt="">
              
            </div>
            <div class="table_img_name" :title="scope.row.name">
              {{ scope.row.name }}
            </div>
            <div class="big_img">
              <img
                :src="scope.row.face_data"
                alt=""
                style="width: 200px; height: 200px"
              >
            </div>
          </div> -->
        </template>
      </el-table-column>
      <el-table-column label="疫情检测信息" align="center">
        <el-table-column
          prop="risk_level"
          label="地区"
          width="60"
          align="center"
        >
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.row.risk_level == '1'"
              icon-class="br-select-di"
            />
            <svg-icon
              v-if="scope.row.risk_level == '2'"
              icon-class="br-select-zhong"
            />
            <svg-icon
              v-if="scope.row.risk_level == '3'"
              icon-class="br-select-gao"
            />
          </template>
        </el-table-column>
        <el-table-column prop="trip" label="行程" width="60" align="center">
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.row.trip == '1'"
              icon-class="br-select-right"
            />
            <svg-icon
              v-if="scope.row.trip == '2'"
              icon-class="br-select-error"
            />
          </template>
        </el-table-column>
        <el-table-column prop="nuclein" label="核酸" width="60" align="center">
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.row.nuclein == '1'"
              icon-class="br-select-right"
            />
            <svg-icon
              v-if="scope.row.nuclein == '2'"
              icon-class="br-select-timeout"
            />
            <svg-icon
              v-if="scope.row.nuclein == '3'"
              icon-class="br-select-error"
            />
          </template>
        </el-table-column>
        <el-table-column prop="survey" label="流调" width="60" align="center">
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.row.survey == '1'"
              icon-class="br-select-right"
            />
            <svg-icon
              v-if="scope.row.survey == '2'"
              icon-class="br-select-error"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="temperature"
          label="体温"
          width="60"
          align="center"
        >
          <template slot-scope="scope">
            <div
              v-if="scope.row.temperature_state == true"
              style="color: #52c41a"
            >
              {{ scope.row.temperature }}
            </div>
            <div
              v-else-if="scope.row.temperature_state == false"
              style="color: #f5222d"
            >
              {{ scope.row.temperature }}
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column prop="state_text" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state_text == '有效'" type="success"
            >有效</el-tag
          >
          <el-tag v-else-if="scope.row.state_text == '无效'" type="info"
            >无效</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="start_time"
        label="陪护开始日期"
        sortable="custom"
        align="center"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.start_time">
            {{ scope.row.start_time | dateFMT }}
          </div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="end_time"
        label="陪护结束日期"
        sortable="custom"
        align="center"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.end_time">
            {{ scope.row.end_time | dateFMT }}
          </div>
          <div v-else>--</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="date"
        label="操作"
        width="180"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.state_text == '有效'"
            v-print="'#printContent'"
            type="text"
            size="small"
            @click="printHandler(scope.row)"
            >打印陪护证</el-button
          >
          <el-button
            v-if="scope.row.state_text == '无效'"
            v-print="'#printContent'"
            disabled
            type="text"
            size="small"
            @click="printHandler(scope.row)"
            >打印陪护证</el-button
          >
          <el-button type="text" size="small" @click="editHandler(scope.row)"
            >编辑</el-button
          >
          <el-button type="text" size="small" @click="detailsHandler(scope.row)"
            >详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="changePage">
      <el-pagination
        :current-page="params.page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="accompany.count"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 登记抽屉 -->
    <Briupdrawer
      :drawer-visible="drawer"
      :title="title"
      width="85%"
      @on-change-visible="handlerVisibleChange"
      @closed="handleClose"
    >
      <div slot="content">
        <div class="drawer_content">
          <el-form
            ref="form"
            :model="form"
            label-width="130px"
            size="small"
            :rules="rules"
            class="demo-ruleForm"
          >
            <el-row>
              <el-col :span="12">
                <div class="drawer_title_text">绑定陪护对象</div>
                <el-form-item label="病区:">
                  {{ this.ward_address }}</el-form-item
                >
                <el-form-item label="病员:" prop="patient">
                  <el-select
                    v-if="form.id"
                    v-model="form.patient"
                    filterable
                    collapse-tags
                    placeholder="请点击或者搜索选择已有病员"
                    style="width: 100%"
                  >
                    <el-option
                      v-for="item in patientData.results"
                      :key="item.id"
                      :label="
                        item.bed_num
                          ? item.bed_num + '-' + item.name
                          : '-' + '-' + item.name
                      "
                      :value="item.id"
                    />
                  </el-select>
                  <el-select
                    v-else
                    ref="get_name_focus"
                    v-model="form.patient"
                    filterable
                    collapse-tags
                    placeholder="请点击或者搜索选择已有病员"
                    style="width: 100%"
                  >
                    <el-option
                      v-for="item in patientData.results"
                      :key="item.id"
                      :label="
                        item.bed_num
                          ? item.bed_num + '-' + item.name
                          : '-' + '-' + item.name
                      "
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
                <div class="drawer_title">陪护人员信息</div>

                <el-form-item label="陪护个人信息:">
                  <!-- 是否重新采集身份证 -->
                  <div v-if="form.id">
                    <el-button
                      v-if="collect == false"
                      plain
                      icon="el-icon-plus"
                      class="dashBtn"
                      @click="collectIDHandler"
                      >重新读取信息</el-button
                    >
                    <el-button
                      v-if="collect == true"
                      :loading="true"
                      plain
                      style="width: 100%"
                      >正在采集</el-button
                    >
                  </div>

                  <div v-else>
                    <el-button
                      v-if="collect == false"
                      id="readIDcard"
                      plain
                      icon="el-icon-plus"
                      class="dashBtn"
                      @click="collectIDHandler"
                      >读取身份证信息</el-button
                    >
                    <el-button
                      v-if="collect == true"
                      :loading="true"
                      plain
                      style="width: 100%"
                      >正在采集</el-button
                    >
                  </div>
                </el-form-item>
                <el-form-item label="陪护姓名:" prop="name">
                  <el-input
                    v-model="form.name"
                    placeholder="请采集陪护人员姓名"
                  />
                </el-form-item>

                <el-form-item label="身份证号:" prop="IDCard">
                  <el-input
                    v-model="form.IDCard"
                    placeholder="请采集陪护人员身份证号"
                  />
                </el-form-item>

                <el-form-item label="电话号码:" prop="phone">
                  <el-input
                    v-model="form.phone"
                    placeholder="请输入陪护人员电话号码"
                  />
                </el-form-item>
                <el-form-item label="地区风险等级:">
                  <br-radio-diqu
                    v-model="form.risk_level"
                    :radio="form.risk_level"
                    @change-radio="changeRadio1"
                  />
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <br-radio-xingcheng
                    v-model="form.trip"
                    :radio="form.trip"
                    @change-radio="changeRadio2"
                  />
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <br-radio-hesuan
                    v-model="form.nuclein"
                    :radio="form.nuclein"
                    @change-radio="changeRadio3"
                  />
                </el-form-item>
                <el-form-item label="流调信息:">
                  <br-radio-liudiao
                    v-model="form.survey"
                    :radio="form.survey"
                    @change-radio="changeRadio4"
                  />
                </el-form-item>
                <el-form-item label="陪护体温:" prop="temperature">
                  <el-input
                    v-model="form.temperature"
                    placeholder="请输入陪护人员体温"
                    ><i slot="suffix">°C </i></el-input
                  >
                </el-form-item>
                <el-form-item label="陪护开始日期:">
                  <el-date-picker
                    v-model="form.start_time"
                    type="date"
                    placeholder="请选择陪护开始日期"
                    style="width: 100%"
                    :picker-options="pickerOptions0"
                  />
                </el-form-item>
                <el-form-item label="陪护结束日期:">
                  <el-date-picker
                    v-model="form.end_time"
                    type="date"
                    placeholder="请选择陪护结束日期"
                    style="width: 100%"
                    :picker-options="pickerOptions0"
                  />
                </el-form-item>
                <el-form-item label="状态:">
                  <el-select v-model="form.state" placeholder="请选择状态">
                    <el-option key="1" label="有效" value="1" />
                    <el-option key="2" label="无效" value="2" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <!-- 人脸采集 -->

                <!-- 修改人脸 -->
                <div class="drawer_title">陪护人脸信息</div>

                <el-form-item
                  v-if="form.id"
                  v-model="form.face_data"
                  label="采集人脸信息:"
                >
                  <img
                    v-if="collectFace == 1"
                    :src="form.face_data"
                    class="escort_face"
                    alt=""
                  />
                  <el-button
                    v-if="collectFace == 1"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    class="dashBtn"
                    @click="collectFaceHandler"
                    >重新采集人脸</el-button
                  >
                  <el-button
                    v-else-if="collectFace == 2"
                    id="camera"
                    :loading="true"
                    plain
                    style="width: 100%"
                    >正在采集</el-button
                  >
                  <el-button
                    v-if="collectFace == 3"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    style="width: 100%"
                    @click="takePhoto"
                    >拍照</el-button
                  >
                  <el-row style="margin-top: 2rem">
                    <el-col :span="11">
                      <video
                        v-if="collectFace != 1"
                        id="video"
                        ref="video"
                        width="100%"
                        height="213px"
                      />
                    </el-col>
                    <el-col :offset="2" :span="11">
                      <canvas
                        v-if="collectFace != 1"
                        id="canvas"
                        ref="canvas"
                        width="200px"
                        height="200px"
                      />
                    </el-col>
                  </el-row>
                </el-form-item>
                <!-- 新增人脸 -->
                <el-form-item v-else label="陪护人脸信息:">
                  <el-button
                    v-if="collectFace == 1"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    class="dashBtn"
                    @click="collectFaceHandler"
                    >采集人脸信息</el-button
                  >
                  <el-button
                    v-else-if="collectFace == 2"
                    id="camera"
                    :loading="true"
                    plain
                    style="width: 100%"
                    >正在采集</el-button
                  >
                  <el-button
                    v-if="collectFace == 3"
                    id="camera"
                    plain
                    icon="el-icon-plus"
                    style="width: 100%"
                    @click="takePhoto"
                    >拍照</el-button
                  >
                  <el-row style="margin-top: 2rem">
                    <el-col :span="11">
                      <video
                        v-if="collectFace != 1"
                        id="video"
                        ref="video"
                        width="200px"
                        height="200px"
                      />
                    </el-col>
                    <el-col :offset="2" :span="11">
                      <canvas
                        v-if="collectFace != 1"
                        id="canvas"
                        ref="canvas"
                        width="200px"
                        height="200px"
                      />
                    </el-col>
                  </el-row>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="
            drawer = false;
            close();
            handleClose();
          "
          >取消</el-button
        >
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </span>
    </Briupdrawer>

    <!-- 详情 -->
    <Briupdrawer
      :drawer-visible="detailDrawer"
      title="陪护人员详情"
      width="45%"
      @on-change-visible="handlerVisibleChange"
    >
      <div slot="content">
        <div class="drawer_content drawer_content_detail">
          <!-- 详情表单 -->
          <el-form
            ref="form"
            :model="detailForm"
            label-width="130px"
            size="mini"
          >
            <el-row>
              <el-col :span="13">
                <el-form-item label="陪护姓名:">
                  {{ detailForm.name }}</el-form-item
                >
                <el-form-item label="身份证号:">
                  {{ detailForm.IDCard }}</el-form-item
                >
                <el-form-item label="电话号码:">
                  {{ detailForm.phone }}</el-form-item
                >
                <el-form-item v-if="detailForm.patient_info" label="病员信息:">
                  {{ this.ward_address }}
                  {{ detailForm.patient_info.bed_num }}号
                  {{ detailForm.patient_info.name }}</el-form-item
                >
                <el-form-item label="地区风险等级:">
                  <div v-if="detailForm.risk_level == '1'">
                    <svg-icon icon-class="br-select-di" />
                    <span class="svg_icon_text">低风险</span>
                  </div>
                  <div v-if="detailForm.risk_level == '2'">
                    <svg-icon icon-class="br-select-zhong" />
                    <span class="svg_icon_text">中风险</span>
                  </div>
                  <div v-if="detailForm.risk_level == '3'">
                    <svg-icon icon-class="br-select-gao" />
                    <span class="svg_icon_text">高风险</span>
                  </div>
                </el-form-item>
                <el-form-item label="14天行程信息:">
                  <div v-if="detailForm.trip == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">绿色</span>
                  </div>
                  <div v-if="detailForm.trip == '2'">
                    <svg-icon icon-class="br-select-error" />
                    <span class="svg_icon_text">红色</span>
                  </div>
                </el-form-item>
                <el-form-item label="核酸检测报告状态:">
                  <div v-if="detailForm.nuclein == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已出</span>
                  </div>
                  <div v-if="detailForm.nuclein == '3'">
                    <svg-icon icon-class="br-select-timeout" />
                    <span class="svg_icon_text">已过期</span>
                  </div>
                  <div v-if="detailForm.nuclein == '2'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="流调信息:">
                  <div v-if="detailForm.survey == '1'">
                    <svg-icon icon-class="br-select-right" />
                    <span class="svg_icon_text">已出</span>
                  </div>
                  <div v-if="detailForm.survey == '2'">
                    <svg-icon icon-class="br-select-error" />
                    <span class="svg_icon_text">未出</span>
                  </div>
                </el-form-item>
                <el-form-item label="陪护体温:">
                  <span
                    v-if="detailForm.temperature >= 37.3"
                    style="color: #f5222d"
                    >{{ detailForm.temperature }}
                  </span>
                  <span
                    v-if="detailForm.temperature < 37.3"
                    style="color: #52c41a"
                    >{{ detailForm.temperature }} </span
                  >°C</el-form-item
                >
              </el-col>
              <el-col :offset="1" :span="10">
                <img class="escort_face" :src="detailForm.face_data" alt="" />
              </el-col>
            </el-row>
            <el-form-item label="陪护日期:">
              {{ detailForm.start_time }} 至
              {{ detailForm.end_time }}</el-form-item
            >
            <el-form-item label="陪护状态:">
              <el-tag
                v-if="detailForm.state_text == '有效'"
                type="success"
                plain
                >有效</el-tag
              >
              <el-tag v-else type="info" plain>无效</el-tag></el-form-item
            >
            <!-- 详情表格 -->
            <div class="drawer_title">通行陪护记录</div>
            <el-table
              :data="traffacRecord_id.results"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
              size="small"
              :header-cell-style="{
                background: '#f5f7fa',
                color: '#606266',
              }"
            >
              <el-table-column
                prop="access_time"
                label="病员信息"
                align="center"
              />
              <el-table-column
                prop="temperature"
                label="陪护通行体温°C"
                width="180"
                align="center"
              >
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.temperature_state == true"
                    style="color: #f5222d"
                  >
                    {{ scope.row.temperature }}
                  </div>
                  <div
                    v-else-if="scope.row.temperature_state == false"
                    style="color: #52c41a"
                  >
                    {{ scope.row.temperature }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="image"
                label="扫描照片"
                align="center"
                width="120"
              >
                <template slot-scope="scope">
                  <div class="show_img">
                    <img :src="scope.row.image" alt="" />
                    <!-- <vue-photo-zoom-pro
                      :url="scope.row.image"
                      :scale="5"
                      :width="40"
                      :out-zoomer="true"
                      :out-zoomer-style="{
                        zIndex: '1000',
                      }"
                      :disabled-reactive="true"
                    ></vue-photo-zoom-pro> -->

                    <div class="big_img_2">
                      <img
                        :src="scope.row.image"
                        alt=""
                        style="width: 200px; height: 200px"
                      />
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
            <div class="loading_more">
              <!-- <el-button plain @click="toLoadingMore">加载更多...</el-button> -->
              <el-pagination
                layout="total, prev, pager, next"
                :total="traffacRecord_id.count"
                style="text-align: center"
                :page-size="params_details.page_size"
                @current-change="changePageDetails"
              />
            </div>
          </el-form>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDrawer = false">取消</el-button>
        <!-- <el-button type="primary" @click="accompanySubmit('123')"
          >确定</el-button
        > -->
      </span>
    </Briupdrawer>

    <!-- 打印陪护证 -->
    <!-- style="display: none" -->
    <div class="block" style="display: none">
      <div id="printContent">
        <div class="phClass">
          <el-row class="title">
            <el-col :span="14">
              <div v-if="form.patient_info">
                {{ form.ward_info.name_abbr }}-{{ form.patient_info.bed_num }}床
              </div>
            </el-col>
            <el-col :span="10">
              <div>陪护证</div>
            </el-col>
          </el-row>

          <div class="block_content">
            <div class="inpatientWard">
              <span style="width: 40px; display: inline-block">病</span>区：{{
                ward_address
              }}
            </div>

            <div v-if="form.patient_info" class="patientName">
              患者姓名：{{ form.patient_info.name }}
            </div>
            <div class="escortName">陪护姓名：{{ form.name }}</div>
            <div
              class="IDCardNum"
              style="white-space: nowrap; display: inline-block"
            >
              <span style="width: 17.5px">身</span
              ><span style="width: 17.5px">份</span>证 ：
              <span>{{ form.IDCard }}</span>
              <!-- <span>999999999999999999</span> -->
            </div>
            <div class="photo">
              <span style="width: 38px; display: inline-block">电</span>话：{{
                form.phone
              }}
            </div>
          </div>
          <div class="tip">
            <div class="tip_title">温馨提示：</div>
            <div>1.每位患者仅限一位陪护；谢绝探视。</div>
            <div>2.陪护证仅限本人使用，不得转接他人。</div>
            <div>3.请随身携带陪护证及身份证。</div>
            <div>4.陪护证上有患者信息，请妥善保管。</div>
            <div>5.陪护证需每日酒精擦拭消毒。</div>
            <div>6.如果离开病区，请把此证归还护士。</div>
          </div>
        </div>

        <div class="block_belong">
          <div class="no6">复旦大学附属中山医院</div>
          <div class="no7">开证日期: {{ form.now_date }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BrRadioXingcheng from '@/components/BrRadioXingcheng'
import BrRadioDiqu from '@/components/BrRadioDiqu'
import BrRadioHesuan from '@/components/BrRadioHesuan'
import BrRadioLiudiao from '@/components/BrRadioLiudiao'
import Briupdrawer from '@/components/Briupdrawer'
import { getWardsId, getWardsName } from '@/utils/auth'
import { mapActions, mapState } from 'vuex'
import moment from 'moment'
export default {
  components: {
    BrRadioXingcheng,
    BrRadioDiqu,
    BrRadioHesuan,
    BrRadioLiudiao,
    Briupdrawer
  },
  data () {
    var valiNumberPass1 = (rule, value, callback) => { // 包含小数的数字
      const reg = /^[+]?(0|([1-9]\d*))(\.\d+)?$/g
      if (value === '') {
        callback(new Error('请输入体温'))
      } else if (!reg.test(value)) {
        callback(new Error('请输入大于0的数字'))
      } else {
        callback()
      }
    }
    return {
      pickerOptions0: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7// 如果没有后面的-8.64e7就是不可以选择今天的
        }
      },
      rules: {
        name: [
          { required: true, message: '请采集姓名', trigger: 'blur' }
        ],
        patient: [
          { required: true, message: '请选择已有病员', trigger: 'change' }
        ],
        IDCard: [
          { required: true, message: '请采集身份证号', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { min: 11, max: 11, message: '请输入11位手机号码', trigger: 'blur' },
          // {
          //   pattern: /^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\d{8}$/,
          //   message: "请输入正确的手机号码"
          // },
          {
            pattern: /^(1)\d{10}$/,
            message: '请输入正确的手机号码'
          }
        ],
        temperature: [
          { required: true, validator: valiNumberPass1, trigger: 'blur' }
        ]
      },
      flag: 0,
      ward_address: getWardsName(),
      params: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId(),
        state: '',
        hospitalization_status: '0',
        ordering: 'patient__bed_num'
      },
      params_details: {
        page: 1,
        page_size: 10,
        ward_id: getWardsId()
      },
      now_date: new Date().toLocaleDateString(),
      active: 1,
      collect: false,
      collectFace: 1,
      printEscortDrawer: false,
      escortDrawer: false,
      detailForm: {},
      detailDrawer: false,
      form: {
        ward_id: getWardsId()
      },
      title: '',
      drawer: false,
      direction: 'rtl',
      radio1: '1',
      radio2: '1',
      radio3: '1',
      radio4: '1',
      input2: '',
      IDCard_form: {}
    }
  },
  computed: {
    ...mapState('accompany', ['accompany']),
    ...mapState('patientManage', ['patientData']),
    ...mapState('traffacRecord', ['traffacRecord', 'traffacRecord_id'])
  },
  filters: {
    // 处理表格日期
    dateFMT (str) {
      return moment(str).format('YYYY-MM-DD')
    }
  },
  watch: {
    'params.state': {
      handler () {
        this.params.page = 1
        this.queryAccompany(this.params)
      },
      deep: true
    },
    'params.hospitalization_status': {
      handler () {
        this.params.page = 1
        this.queryAccompany(this.params)
      },
      deep: true
    },
    params: {
      handler () {
        this.queryAccompany(this.params)
      },
      deep: true
    }
  },
  created () {
    this.queryAccompany(this.params)
    // 所有病员
    this.queryPatientData({ ward_id: getWardsId(), hospitalization_status: 0, page: 1, page_size: 1000, ordering: 'bed_num' })
  },
  methods: {
    ...mapActions('accompany', ['queryAccompany', 'saveAccompany', 'updateAccompany']),
    ...mapActions('patientManage', ['queryPatientData']),
    ...mapActions('traffacRecord', ['queryPersonlmgtThoughtId']),
    ...mapActions('patientManage', ['exportList']),
    handleClose () {
      this.$refs.form.resetFields()
    },
    // 排序
    sortHandler (column) {
      if (column.column.label == '病员信息') {
        if (column.order == 'ascending') { // 上箭头
          var obj = {
            ordering: 'patient__bed_num'
          }
        } else if (column.order == 'descending') { // 下箭头
          var obj = {
            ordering: '-patient__bed_num'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryAccompany(this.params)
      } else if (column.column.label == '陪护开始日期') {
        if (column.order == 'ascending') { // 上箭头
          var obj = {
            ordering: 'start_time'
          }
        } else if (column.order == 'descending') { // 下箭头
          var obj = {
            ordering: '-start_time'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryAccompany(this.params)
      } else if (column.column.label == '陪护结束日期') {
        if (column.order == 'ascending') { // 上箭头
          var obj = {
            ordering: 'end_time'
          }
        } else if (column.order == 'descending') { // 下箭头
          var obj = {
            ordering: '-end_time'
          }
        }
        this.params = Object.assign(this.params, obj)
        this.queryAccompany(this.params)
      }
    },

    // 采集身份证信息
    collectIDHandler () {
      this.collect = true
      setTimeout(() => {
        this.collect = false
      }, 500)
      var httpServerPort = 8989
      $('#divInfo').html('')
      $('#img').attr('src', '')
      $.ajax({
        dataType: 'JSONP',
        type: 'get',
        url: 'http://localhost:' + httpServerPort + '/api/ReadMsg?waitTime=3&readOnce=1', // 接口服务器地址
        success: (data) => {
          this.IDCard_form = data
        },
        error (e) {
          // 失败执行
          alert(e.status + ',' + e.statusText)
        }
      })
      setTimeout(() => {
        this.form.name = this.IDCard_form.name
        this.form.IDCard = this.IDCard_form.cardno
        this.$forceUpdate()
      }, 1000)
    },

    // 打印陪护证
    printHandler (row) {
      this.form = row
      this.form.now_date = moment(new Date()).format('YYYY年MM月DD日')
    },

    // 保存
    onSubmit () {
      if (this.flag == 0) {
        // 编辑保存
        const form = Object.assign({}, this.form)
        // form.patient = form.patient
        form.ward_id = getWardsId()
        form.state = Number(form.state)
        form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
        if (form.end_time) {
          form.end_time = moment(form.end_time).format('YYYY-MM-DD hh:mm:ss')
        }
        this.updateAccompany(form).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.drawer = false
            this.form = {}
            this.close()
          }
          if (res.code == 202) {
            this.$message({
              message: res.msg,
              type: 'warning'
            })
            this.drawer = false
            this.form = {}
            this.close()
          }
          if (res.detail) {
            this.$message.error(res.detail)
            this.drawer = true
          }
          this.queryAccompany(this.params)
        })
      } else if (this.flag == 1) {
        this.$refs.form.validate((valid) => {
          if (valid) {
            // 新增保存
            const form = Object.assign({}, this.form)
            // form.patient = form.patient
            form.start_time = moment(form.start_time).format('YYYY-MM-DD hh:mm:ss')
            if (form.end_time) {
              form.end_time = moment(form.end_time).format('YYYY-MM-DD hh:mm:ss')
            }
            this.saveAccompany(form).then((res) => {
              this.drawer = false
              if (res.code == 200) {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.close()
              }
              // if (res.code == 202) {
              //   this.$message({
              //     message: res.msg,
              //     type: 'warning'
              //   })
              // }

              if (res.code == 400) {
                // if (res.msg == '该病员陪护人员数量达到上限') {
                this.$message.error(res.msg)
                this.drawer = true
                // }
                // else if (res.details == '设备连接失败')
                //   this.$message.error(res.msg);
              }
              this.queryAccompany(this.params)
            })
              .catch((error) => {
                console.log(error)
                if (error.status == 400) {
                  this.$message.error(error.data.msg)
                } else {
                  this.$message({
                    message: error.data.msg,
                    type: 'warning'
                  })
                }
              })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      }
    },

    // 登记
    toAddHandler () {
      this.flag = 1
      this.collectFace = 1
      this.drawer = true
      this.title = '陪护人员登记'
      this.form = {
        ward_id: getWardsId()
      }
      this.form.state = '1'
      this.form.start_time = this.now_date
      // 聚焦
      setTimeout(() => {
        this.$refs.get_name_focus.focus()
      }, 700)
      /**
       * 地区风险等级默认值
       */
      this.changeRadio1(1)
      this.changeRadio2(1)
      this.changeRadio3(1)
      this.changeRadio4(1)
    },

    // 编辑
    editHandler (row) {
      this.drawer = true
      this.flag = 0
      this.collectFace = 1
      this.title = '陪护人员编辑'
      this.form = row
      // 新增一个对象，判断后改变该对象中的值，再与form对象合并
      var obj = { state: 0, patient: 0 }
      if (row.state_text == '有效') {
        obj.state = '1'
      } else if (row.state_text == '无效') {
        obj.state = '2'
      }
      obj.patient = row.patient_info.id
      this.form = Object.assign({}, this.form, obj)
    },

    // 详情
    detailsHandler (row) {
      this.detailDrawer = true
      this.detailForm = row
      // 当前陪护人陪护通行记录
      this.params_details.attendant__id = this.detailForm.id
      console.log(this.params_details)
      this.queryPersonlmgtThoughtId(this.params_details)
    },

    // 详情分页
    changePageDetails (page) {
      this.params_details.page = page
      this.params_details.attendant__id = this.detailForm.id

      this.queryPersonlmgtThoughtId(this.params_details)
    },

    // 分页
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.params.page_size = val
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.params.page = val
    },

    // 详情关闭
    accompanySubmit () {
      this.detailDrawer = false
    },

    // 采集人脸
    collectFaceHandler () {
      this.collectFace = 1
      setTimeout(() => {
        this.collectFace = 2
      }, 200)
      setTimeout(() => {
        this.collectFace = 3
      }, 2000)
      this.myVideo()
      if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
        // 调用用户媒体设备, 访问摄像头
        close()
        var device_videos = this.getData().device_videos
        var device_videos_index = this.getData().device_videos_index
        var success = this.success
        var error = this.error
        setTimeout(() => {
          // width: video.videoWidth, height: video.videoHeight,
          this.getUserMedia({ video: { width: 200, height: 200, deviceId: device_videos[device_videos_index++ % device_videos.length] } }, success, error)

        }, 1000)
      } else {
        alert('不支持访问用户媒体')
      }
    },

    myVideo () {
      return this.$refs['video']
    },
    myContext () {
      return this.$refs['canvas'].getContext('2d')
    },
    // 采集人脸
    // 访问用户媒体设备的兼容方法
    getUserMedia (constraints, success, error) {
      if (navigator.mediaDevices.getUserMedia) {
        // 最新的标准API
        navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error)
      } else if (navigator.webkitGetUserMedia) {
        // webkit核心浏览器
        navigator.webkitGetUserMedia(constraints, success, error)
      } else if (navigator.mozGetUserMedia) {
        // firfox浏览器
        navigator.mozGetUserMedia(constraints, success, error)
      } else if (navigator.getUserMedia) {
        // 旧版API
        navigator.getUserMedia(constraints, success, error)
      }
    },

    success (stream) {
      // 兼容webkit核心浏览器
      const CompatibleURL = window.URL || window.webkitURL
      // 将视频流设置为video元素的源
      this.myVideo()
      // video.src = CompatibleURL.createObjectURL(stream);
      video.srcObject = stream
      video.play()
    },

    error (error) {
      console.log(`访问用户媒体设备失败${error.name}, ${error.message}`)
    },

    close () { // 关闭摄像头数据流
      if (this.$refs['video']) {
        if (this.$refs['video'].srcObject) {
          try {
            var tracks = this.$refs['video'].srcObject.getTracks()
            for (var track of tracks) {
              track.stop()
            }
          } catch (e) {
            alert(e)
          }
        }
      }
    },

    convertCanvasToImage (canvas) {
      // 新Image对象,可以理解为DOM;
      var image = new Image()
      // canvas.toDataURL返回的是一串Base64编码的URL,当然,浏览器自己肯定支持
      // 指定格式PNG
      image.src = canvas.toDataURL('image/png')
      return image
    },

    // 设备摄像头数据获取
    getData () {
      var device_videos = []
      var device_videos_index = 0
      navigator.mediaDevices.enumerateDevices().then(function (devices) {
        devices.forEach(function (dv) {
          var kind = dv.kind
          if (kind.match(/^video.*/)) {
            device_videos.push(dv.deviceId)
          }
        })
      })
      var device = {
        device_videos,
        device_videos_index
      }
      return device
    },

    // 拍照
    takePhoto () {
      var context = this.myContext()
      // context.drawImage(video, 0, 0, video.videoWidth / 3, video.videoHeight / 3);
      context.drawImage(video, 0, 0, 200, 200)
      // 图片
      const base64 = this.convertCanvasToImage(canvas)
      var data = window.atob(base64.src.split(',')[1])
      const ia = new Uint8Array(data.length)
      for (let i = 0; i < data.length; i++) {
        ia[i] = data.charCodeAt(i)
      }
      const blob = new Blob([ia], { type: 'image/jpeg' })
      this.form.face_data = blob
    },

    // 处理visible变化的钩子函数，解决抽屉无法收缩的bug
    handlerVisibleChange (val) {
      this.drawer = val
      this.printEscortDrawer = val
      this.escortDrawer = val
      this.detailDrawer = val
      this.close()
      this.handleClose()
    },

    changeRadio1 (data) {
      // 获取子组件的label
      this.form.risk_level = data
      this.form = Object.assign({}, this.form)
    },
    changeRadio2 (data) {
      // 获取子组件的label
      this.form.trip = data
      console.log(this.radio2)
      this.form = Object.assign({}, this.form)
    },
    changeRadio3 (data) {
      // 获取子组件的label
      this.form.nuclein = data
      console.log(this.radio3)
      this.form = Object.assign({}, this.form)
    },
    changeRadio4 (data) {
      // 获取子组件的label
      this.form.survey = data
      console.log(this.radio4)
      this.form = Object.assign({}, this.form)
    }
  }

}
</script>

<style scoped media="printContent">
/*控制显示文本的长度，超过长度使用省略号*/
.drawer_content >>> .el-table__body-wrapper {
  overflow: visible !important;
}
/* .el-table .cell {
  overflow: visible !important;
}
.el-table {
  overflow: visible !important;
} */
.accompanies >>> .el-radio__input.is-checked + .el-radio__label {
  color: #606266 !important;
}
@page {
  margin: 3mm; /* this affects the margin in the printer settings */
}
html {
  background-color: #ffffff;
  margin: 0px;
}
.tip {
  margin-top: 2rem;
}
.tip div {
  font-size: 10px;
  /* -webkit-transform: scale(0.9); */
  white-space: nowrap;
  margin-bottom: 0.1rem;
}
.block {
  border: solid 1px blue;
  width: 50mm;
  height: 80mm;
  margin: 0 auto; /* margin you want for the content */
}
#printContent {
  font-size: 10px;
  text-align: left;
}
.block_content {
  margin-top: 2rem;
}
.block_content div {
  font-size: 13px;
  /* -webkit-transform: scale(0.9); */
  font-weight: 500;
  line-height: 16px;
  width: 50mm;
  font-family: "Arial Narrow", Arial, sans-serif;
}
.phClass {
  color: black;
}
.phClass .title {
  text-align: center;
  font-size: 22px;
  font-weight: 400;
}
.IDCardNum span {
  display: inline-block;
  white-space: nowrap;
}
.block_belong div {
  float: right;
}
.block_belong {
  margin-top: 1.5rem;
  font-size: 10px;
  /* -webkit-transform: scale(0.9); */
}

.accompanies >>> .el-input {
  /* width: 100%; */
}
/* 去表格边框 */
.accompanies >>> .el-table {
  border: none;
}
.accompanies >>> .el-table::after {
  width: 0;
}
.accompanies >>> .el-table td {
  border-right: none !important;
}
.accompanies >>> .el-table__header-wrapper th {
  border-right: none !important;
  border-left: none !important;
}
/* 登记抽屉 */
.drawer_content .el-col:first-child .el-form-item:nth-child(11) .el-select,
.patient_select {
  width: 100%;
}
/* 进度条打印陪护证 */
.escortSuccess div:nth-child(3) {
  font-weight: 500;
  font-size: 14px;
}
.escortSuccess div:nth-child(2) {
  font-size: 22px;
  margin: 1.2rem 0 1rem 0;
  font-weight: bolder;
}
.el-steps--horizontal {
  padding: 0 25%;
  height: 60px;
  margin-top: 2rem;
}
.escortSuccess {
  height: 200px;
  position: relative;
  margin-top: 4rem;
}
.escortSuccess div {
  text-align: center;
}
.escortSuccess div:first-child {
  height: 70px;
}

.escortSuccess div:first-child .svg-icon {
  /* width: 20px;
  height: 20px; */
  position: absolute;
  left: 50%;
  margin-left: -25px;
}

/* 打印陪护证 */

.accompanies >>> .image-slot {
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 1000;
}
.el-image {
  position: static;
}
/* 详情 */
.loading_more >>> .el-button {
  margin-left: 43%;
}
.drawer_content_detail .el-form-item:nth-child(9) span {
  color: #6dc442;
  font-size: 16px;
  font-weight: 500;
}

.svg_icon_text {
  float: left;
  margin-left: 0.2rem;
}
.drawer_content_detail >>> .svg-icon {
  float: left;
  height: 32px;
}
.escort_face {
  width: 200px;
  height: 200px;
}
.table_img_name {
  float: left;
  width: 100px;
  height: 46px;
  cursor: pointer;
  line-height: 46px;
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.table_img {
  margin: 0 1rem 0 0.8rem;
  float: left;
  line-height: 23px;
}
/* .accompanies >>> .el-table--border th,
.el-table--border td {
  border-right: none;
} */
.accompanies >>> .el-table--small th,
.accompanies >>> .el-table--small td {
  padding: 5px 0 0 0;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
}
.accompanies >>> .el-table img {
  width: 40px;
  height: 40px;
}
.drawer_content .dashBtn:hover,
.dashBtn:focus {
  border-color: #1890ff;
}
.dashBtn {
  border: 1px dashed #dcdfe6;
  width: 100%;
}
.drawer_title_text {
  margin: 0.8rem 0 0.5rem 20px;
  color: #272727;
  font-weight: bolder;
}
.el-form-item {
  margin-bottom: 14px;
}
.accompanies >>> .el-drawer {
  /* overflow: auto; */
}
.accompanies >>> .el-drawer__wrapper {
  /* overflow: auto; */
}
.drawer_content {
  padding: 0 30px;
}
.svg-icon {
  width: 1.2rem;
  height: 1.2rem;
}
.accompanies >>> .el-drawer__header > :first-child,
.drawer_title {
  color: #272727;
  font-weight: 700;
}
.drawer_title {
  margin-bottom: 1rem;
}
.accompanies >>> .el-divider--horizontal {
  margin: 12px 0;
}
.accompanies >>> .el-drawer__header {
  margin-bottom: 0;
  padding: 10px 0 0 30px;
}
.changeEscort {
  margin-left: 1rem;
  color: #1890ff;
}
.editCell:hover {
  cursor: pointer;
}
.el-table {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3rem;
}
/* .changePage {
  position: absolute;
  bottom: 1rem;
  left: 50%;
  margin: 1rem 0 0 -202px;
} */
.gender {
  margin-right: 0.5rem;
  display: inline-block;
}
.header .el-button {
  font-size: 14px;
  font-weight: 700;
}
</style>
