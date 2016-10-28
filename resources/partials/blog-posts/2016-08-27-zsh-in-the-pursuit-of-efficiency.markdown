<!---
title:  "Zsh: in the pursuit of efficiency"
description: Zsh as part of development kit
layout: blog_post 
categories: zsh
-->

__Zsh__ - a very powerful advanced shell significantly increases your efficiency as a developer.

## Zsh and iTerm2

We will use zsh in combination with [iTerm2](http://iterm2.com/features.html). We will also setup zsh completions using homebrew.

```clojure
brew update
brew tap caskroom/homebrew-cask
brew install brew-cask
brew cask install iterm2

brew install zsh zsh-completions
echo "/usr/local/bin/zsh" | sudo tee -a /etc/shells
chsh -s /usr/local/bin/zsh
```


## Oh My Zsh

Now let's setup "Oh My Zsh" framework. This framework includes a lot of plugins and themes allowing customization to suit every taste. 

```clojure
sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
```


### Choose your theme

We will use the "agnoster" theme as an example. For iTerm2 you will need to download Solarized Dark:

```clojure
git clone https://github.com/CyberLight/agnoster.zsh-theme
```

Import Solarized Dark.itermcolors from the previously downloaded archive.

*iTerm2 menu -> Preferences -> Profiles -> Colors -> Color Presets -> Import*

<img src="https://s3.amazonaws.com/blog-images.epxlabs.com/7/iterm_profiles_colors.png" width="600">

After the import it should appear in a list of presets:

<img src="https://s3.amazonaws.com/blog-images.epxlabs.com/7/iterm_profiles_colors_presets.png" width="200">


#### Install fonts

Some themes, like "agnoster" in our case, need to install additional fonts. Run the next script to do this:

```clojure
git clone https://github.com/powerline/fonts.git
cd fonts
# next addresses could change. If so please check https://github.com/CyberLight/agnoster.zsh-theme
mkdir -p others

fonts=('https://gist.github.com/qrush/1595572/raw/6c453ddc959b93895ffbf4fd904e2ba60256c904/Inconsolata-dz-Powerline.otf' \
       'https://gist.github.com/qrush/1595572/raw/6c453ddc959b93895ffbf4fd904e2ba60256c904/Menlo-Powerline.otf' \
       'https://gist.github.com/qrush/1595572/raw/6c453ddc959b93895ffbf4fd904e2ba60256c904/mensch-Powerline.otf' \
       'https://github.com/powerline/powerline/raw/develop/font/PowerlineSymbols.otf' \
)

for i in $fonts; do
  wget $i -P others/
done
./install.sh
```

Change the font in iTerm2:

*iTerm2 menu -> Preferences -> Profiles -> Text -> Change Font (for Regular font and for Non-ASCII)*

<img src="https://s3.amazonaws.com/blog-images.epxlabs.com/7/iterm_profiles_text.png" width="600">


#### Set theme and plugins

Update your ~/.zshrc with:

```clojure
ZSH_THEME="agnoster"
plugins=(git colored-man-pages colorize virtualenv pip python brew osx zsh-syntax-highlighting)
```

Your current console should look like:

<img src="https://s3.amazonaws.com/blog-images.epxlabs.com/7/iterm_zsh_agnoster.png" width="600">
