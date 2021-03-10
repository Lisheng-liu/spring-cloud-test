package com.lls.jsch;

import com.jcraft.jsch.*;


import java.io.*;
import java.util.Properties;

public class JschTest {



    public Session getSshSession(SshInfo sshInfo ){
        JSch jSch = new JSch();
        Session session=null;
        try{
            session = jSch.getSession(sshInfo.getUserName(),sshInfo.getIp(),sshInfo.getPort());
            session.setPassword(sshInfo.getPassword());
            Properties conf = new Properties();
            conf.setProperty("StrictHostKeyChecking","no");
            session.setConfig(conf);
            session.connect();

        } catch (JSchException e) {
            e.printStackTrace();
        }
        return session;
    }

    /**
     * 其他操作系统执行linux 命令
     * @param sshInfo
     * @param linuxCmd
     */
    public void exeLinuxCmd(SshInfo sshInfo ,String linuxCmd){
        Session session = null;
        ChannelExec exec = null;
        try{
            session = getSshSession(sshInfo);
            exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand(linuxCmd);
            exec.setInputStream(null);
            exec.setErrStream(System.err);
            exec.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String msg = null;
            while ((msg = bufferedReader.readLine()) != null){
                System.out.println(String.format("执行命令$%1$s返回消息%2$s",linuxCmd,msg));
            }

        } catch (JSchException | FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(exec != null && exec.isConnected()){
                exec.disconnect();
            }
            if(session != null && session.isConnected()){
                session.disconnect();
            }
        }

    }

    /**
     * 其他操作系统向linux 系统传文件
     * @param sshInfo
     * @param localFilePath
     * @param remoteDirPath
     * @throws IOException
     */
    public void uploadFile(SshInfo sshInfo,String localFilePath,String remoteDirPath) throws IOException {
        Session session = null;
        ChannelSftp sftp = null;
        InputStream inputStream = null;
        try{
            session = getSshSession(sshInfo);
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.cd(remoteDirPath);

            File file = new File(localFilePath);
            if(file.exists()){
                inputStream = new FileInputStream(file);
                sftp.put(inputStream,file.getName());
            }
        } catch (JSchException | FileNotFoundException | SftpException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(sftp != null && sftp.isConnected()){
                sftp.disconnect();
            }
            if(session != null && session.isConnected()){
                session.disconnect();
            }
        }


    }







    class SshInfo{
        private String userName;
        private String password;
        private String ip;
        private int port;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }



}
