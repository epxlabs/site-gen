# Zsh: colorize man pages

Recently we explored how to introduce many beautiful bright colors to your zsh install, let's check our man pages.

If you didn't add "colored-man-pages" to the list of plugins in ~/.zshrc you will see the the following first image, if you have added than you will see the second. But how do we get third?

<table>
  <tr>
    <td>
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/8/colored-man-pages-off.png">
    </td>
    <td>
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/8/colored-man-pages.png">
    </td>
    <td>
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/8/colored-man-pages-custom.png">
    </td>
  </tr>
</table>

Let's talk a little about how plugins work inside oh-my-zsh.

There are 2 directories in $ZSH:

* plugins - for all things which came with your vanilla oh-my-zsh install
* custom/plugins - you should use this directory when want to override anything from the default setup

So, to set up our own color scheme we can start by copying default plugins to our directory:

```clojure
cd ~/.oh-my-zsh/custom/plugins/
cp -r ../../plugins/colored-man-pages .
```

And update it a little, as follows:

```clojure
if [[ "$OSTYPE" = solaris* ]]
then
    if [[ ! -x "$HOME/bin/nroff" ]]
    then
        mkdir -p "$HOME/bin"
        cat > "$HOME/bin/nroff" <<EOF
#!/bin/sh
if [ -n "\$_NROFF_U" -a "\$1,\$2,\$3" = "-u0,-Tlp,-man" ]; then
    shift
    exec /usr/bin/nroff -u\$_NROFF_U "\$@"
fi
#-- Some other invocation of nroff
exec /usr/bin/nroff "\$@"
EOF
        chmod +x "$HOME/bin/nroff"
    fi
fi

man() {
    env \
        LESS_TERMCAP_mb=$(printf '\e[01;31m') \ # enter blinking mode - red
        LESS_TERMCAP_md=$(printf '\e[01;35m') \ # enter double-bright mode - bold, magenta
        LESS_TERMCAP_me=$(printf '\e[0m') \     # turn off all appearance modes (mb, md, so, us)
        LESS_TERMCAP_se=$(printf '\e[0m') \     # leave standout mode
        LESS_TERMCAP_so=$(printf '\e[01;33m') \ # enter standout mode - yellow
        LESS_TERMCAP_ue=$(printf '\e[0m') \     # leave underline mode
        LESS_TERMCAP_us=$(printf '\e[04;36m') \ # enter underline mode - cyan
        PAGER="${commands[less]:-$PAGER}" \
        _NROFF_U=1 \
        PATH="$HOME/bin:$PATH" \
            man "$@"
}
```
<br>

## ANSI color sequences

The color sequences are composed of sequences of numbers separated by semicolons.

The most common codes are:

<table>
  <tr><td width="10%"> Code </td><td width="30%"> Note </td>
    <td rowspan="20"><img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/8/colors.png"></td></tr>
  <tr><td> 0    </td><td> to restore default color         </td></tr>
  <tr><td> 1    </td><td> for brighter colors	           </td></tr>
  <tr><td> 4    </td><td> for underlined text              </td></tr>
  <tr><td> 5    </td><td> for flashing text                </td></tr>
  <tr><td> 30   </td><td> for black foreground	           </td></tr>
  <tr><td> 31   </td><td> for red foreground	           </td></tr>
  <tr><td> 32   </td><td> for green foreground	           </td></tr>
  <tr><td> 33   </td><td> for yellow (or brown) foreground </td></tr>
  <tr><td> 34   </td><td> for blue foreground	           </td></tr>
  <tr><td> 35   </td><td> for purple foreground	           </td></tr>
  <tr><td> 36   </td><td> for cyan foreground	           </td></tr>
  <tr><td> 37   </td><td> for white (or gray) foreground   </td></tr>
  <tr><td> 40   </td><td> for black background             </td></tr>
  <tr><td> 41   </td><td> for red background               </td></tr>
  <tr><td> 42   </td><td> for green background             </td></tr>
  <tr><td> 43   </td><td> for yellow (or brown) background </td></tr>
  <tr><td> 44   </td><td> for blue background              </td></tr>
  <tr><td> 45   </td><td> for purple background            </td></tr>
  <tr><td> 46   </td><td> for cyan background              </td></tr>
  <tr><td> 47   </td><td> for white (or gray) background   </td></tr>
</table>
<br>

__Run this in your terminal:__

```clojure
#!/bin/sh
echo -e "\033[00;30m 00;30m \033[00m \033[02;30m 02;30m \033[00m"
echo -e "\033[00;31m 00;31m \033[00m \033[02;31m 02;31m \033[00m"
echo -e "\033[00;32m 00;32m \033[00m \033[02;32m 02;32m \033[00m"
echo -e "\033[00;33m 00;33m \033[00m \033[02;33m 02;33m \033[00m"
echo -e "\033[00;34m 00;34m \033[00m \033[02;34m 02;34m \033[00m"
echo -e "\033[00;35m 00;35m \033[00m \033[02;35m 02;35m \033[00m"
echo -e "\033[00;36m 00;36m \033[00m \033[02;36m 02;36m \033[00m"
echo -e "\033[00;37m 00;37m \033[00m \033[02;37m 02;37m \033[00m"
echo -e "\033[01;30m 01;30m \033[00m \033[01;40m 01;40m \033[00m"
echo -e "\033[01;31m 01;31m \033[00m \033[01;41m 01;41m \033[00m"
echo -e "\033[01;32m 01;32m \033[00m \033[01;42m 01;42m \033[00m"
echo -e "\033[01;33m 01;33m \033[00m \033[01;43m 01;43m \033[00m"
echo -e "\033[01;34m 01;34m \033[00m \033[01;44m 01;44m \033[00m"
echo -e "\033[01;35m 01;35m \033[00m \033[01;45m 01;45m \033[00m"
echo -e "\033[01;36m 01;36m \033[00m \033[01;46m 01;46m \033[00m"
echo -e "\033[01;37m 01;37m \033[00m \033[01;47m 01;47m \033[00m"
```
