<p align="center">
	<strong>精彩享食</strong>
</p>
<p align="center">
    <a target="blank" href="https://github.com/Helltractor/enjoy-take-out">
        <img src="https://img.shields.io/github/stars/Helltractor/enjoy-take-out.svg?style=social" alt="github star"/>
    </a>
    <a target="_blank" href="https://opensource.org/licenses/MIT">
        <img src="https://img.shields.io/:license-MIT-blue.svg" alt="license"/>
    </a>
    <a target="_blank" href="https://github.com/Helltractor/Securities-Trading-System">
        <img src='https://img.shields.io/badge/JDK-1.8.0_40+-green.svg' alt='jdk'/>
    </a>
<p/>
<p align="center">
    <a target="blank" href="https://github.com/Helltractor/Securities-Trading-System">
        <img src='https://img.shields.io/badge/Maven-3.9.6-blue.svg' alt='maven'/>
    </a>
    <a target="_blank" href="https://github.com/Helltractor/Securities-Trading-System">
        <img src='https://img.shields.io/badge/Spring%20Boot-2.7.3-green.svg' alt='spring boot'/>
    </a>
</p>

# Build container and compile dependencies（in Terminal）

1. `mvn clean install`
2. `cd ./enjoy-build`
3. `docker-compose up --d`

# Run application（in IntelliJ IDEA）

1. run `EnjoyApplication.java`
2. open `http://localhost/#/login` in browser

# Run jar package

1. `java -jar enjoy-server-0.0.1.jar`
2. open `http://localhost/#/login` in browser

# Next steps

- [x] 打包成jar包(0.0.1)，参考[javafx的maven项目打jar包配置](https://www.bingbaihanji.com/archives/javafxPackagingConfiguration#javafx%E7%9A%84maven%E9%A1%B9%E7%9B%AE%E6%89%93jar%E5%8C%85%E9%85%8D%E7%BD%AE)
- [ ] 数据库无数据时，能够导出数据表格
- [ ] 数据库主从复制，参考[使用docker-compose配置mysql主从备份](https://www.enjoytoday.cn/2024/01/11/%E4%BD%BF%E7%94%A8docker-compose%E9%85%8D%E7%BD%AEmysql%E4%B8%BB%E4%BB%8E%E5%A4%87%E4%BB%BD/)
