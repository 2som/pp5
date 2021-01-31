import React from "react";
import { Layout, Menu } from 'antd';
import {
  UserOutlined,
  UnlockOutlined
} from '@ant-design/icons';

import { Link, withRouter } from "react-router-dom";

const { Sider } = Layout;

const CustomSider = () => (
  <Sider style={{ paddingTop: '5vh' }} collapsed={false}>
    <Menu theme="dark" defaultSelectedKeys={['2']} mode="inline">
      <Menu.Item key="1" icon={<UnlockOutlined /> }>
        <Link to="/products">Admin Panel</Link>
      </Menu.Item>
      <Menu.Item key="2" icon={<UserOutlined />}>
      <Link to="/">Client View</Link>
      </Menu.Item>
    </Menu>
  </Sider>
);

export default withRouter(CustomSider);