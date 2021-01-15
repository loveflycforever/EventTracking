cd /
# 备份
mkdir -p /srv/history | find /srv -maxdepth 1 -name house-microshop-acq-*.jar | sed 's#.*/##' | xargs -r -t -i mv -b /srv/{} /srv/history/{}.bak@`date +%Y%m%d%H%M%S`
# 移动
ls -lt /srv/newload/house-microshop-acq-*.jar | head -n 1 | awk '{print $9}' | sed 's#.*/##' | xargs -r -t -i mv -b /srv/newload/{} /srv/{}
# 杀死
ps -ef | grep /srv/house-microshop-acq- | grep -v grep | awk '{print $2}' | xargs -r -t -i kill -9 {}
# 运行
ls -lt /srv/house-microshop-acq-*.jar | head -n 1 | awk '{print $9}' | xargs -r -t -I housejarname sh -c 'nohup java -jar housejarname --spring.config.additional-location=/srv/config/et_acq/application-prod.properties --spring.profiles.active=prod &'
