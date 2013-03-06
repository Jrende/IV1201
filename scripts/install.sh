#!/bin/bash
#
#
default_install_path=/opt/www-recruiter
default_www_user=www-data
default_www_group=www-data
default_www_service_port=9000

# 
# Parse command line
#
while (($#)) ; do   
        case "$1" in
		--install-path | -i )
			shift 1
			install_path=$(readlink $1)
			if [ ! -d $install_path ] ; then
				echo "Bad install path. No such directory: $install_path"
			fi
			;;
		--uninstall | -u )
			uninstall=1;
			;;
		--group | -g )
			shift 1
			www_group=$1
			;;
		--user | -u )
			shift 1
			www_user=$1
			;;
		--port | -p )
			shift 1
			www_service_port=$1
			;;
		--help | -h )
			echo "${0##*/}: Usage [options]"
			echo -e "\t--install-path, -i Install program into path."
			echo -e "\t--uninstall, -u    Uninstall program."
			echo -e "\t--group, -g        Set group owner of program."
			echo -e "\t--user, -u         Set user owner of program."
			echo -e "\t--port, -p         Start program using port."
			echo -e "\t--help, -h         Print this help."
			exit 1
			;;
                * )
                        echo "error: Unknown option $1"
                        exit 1
                        ;;
        esac

        shift 1;
done

if [ $(id -u) -ne 0 ] ; then
	echo "You must run this script as root."
	exit 1
fi

if [ $uninstall ] ; then
	echo "Uninstalling..."
	# Stop service if running.
	/etc/init.d/www-recruiter stop
	# Remove program from rc.d
	update-rc.d -f www-recruiter remove
	
	# Get install path and remove relevant files and directories.
	. /etc/www-recruiter/settings
	rm -f /etc/init.d/www-recruiter
	rm -f /etc/www-recruiter
	rm -rf $INSTALL_PATH
	
	exit 1;
fi



#
# Get install path
#
if [ ! $install_path ] ; then
	echo -n "Enter install path [$default_install_path]: "
	read $install_path

	if [ ! $install_path ] ; then
		install_path=$default_install_path
	fi
fi

#
# Get file user owner
#  
if [ ! $www_user ] ; then
	echo -n "As wich user do you want to run the application framework [$default_www_user]: "
	read www_user
	
	if [ ! $www_user ] ; then
		www_user=$default_www_user
	fi
fi

#
# Get file group owner
#
if [ ! $www_group ] ; then
	echo -n "As which group do you want to run the application framework [$default_www_group]: "
	read www_group
	
	if [ ! $www_group ] ; then
		www_group=$default_www_group
	fi
fi

#
# Get www service port
#
if [ ! $www_service_port ]; then
	echo -n "Set www service port [$default_www_service_port]: "
	read www_service_port
	
	if [ ! $www_service_port ] ; then
		www_service_port=$default_www_service_port
	fi
fi

#
# Install required programs.
which screen > /dev/null
if [ $? -ne 0 ]; then
	echo "Installing screen..."
	apt-get install screen
fi

#
# Check if user www_user already exists.
#
id -u $www_user > /dev/null
[ $? -ne 0 ] && useradd -r $www_user

#
# Check if group www_group already exists.
#
id -g $www_group > /dev/null
[ $? -ne 0 ] && groupadd -r $www_group


echo "Copying files to: $install_path"
mkdir -p $install_path
cp -rd $(readlink -f .)/* $install_path

echo "Setting file permissions."
chown -R root:root $install_path
chown -R $www_user:$www_group $install_path/site-data
chown -R $www_user:$www_group $install_path/playframework
#
# Make shore file and directory permissions are good.  
for dir in $(find $install_path/site-data -type d) ; do
        chmod 775 "$dir"
done

for file in $(find $install_path/site-data -type f) ; do
	chmod 644 "$file"
done


echo "Configuring up startup..."
ln -s $install_path/conf /etc/www-recruiter
ln -s $install_path/www-recruiter /etc/init.d/www-recruiter

echo "INSTALL_PATH=\"$install_path\"" > $install_path/conf/settings
echo "SERVICE_PORT=\"$www_service_port\"" >> $install_path/conf/settings
echo "RUN_USER=\"$www_user\"" >> $install_path/conf/settings
echo "RUN_GROUP=\"$www_group\"" >> $install_path/conf/settings

chmod +x $install_path/conf/settings
chmod +x $install_path/www-recruiter
update-rc.d www-recruiter defaults

