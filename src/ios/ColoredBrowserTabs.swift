/**
 MIT License
 
 Copyright (c) 2019 Tobias Ebert
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 
 ----------------------------------------------------------------------------------
 
 ColoredBrowserTabs.swift
 SafariViewControllerTest
 
 Created by Tobias Ebert on 13.01.19.
 Copyright © 2019 Tobias Ebert. All rights reserved.
 **/
import SafariServices

@objc(ColoredBrowserTabs) class ColoredBrowserTabs : CDVPlugin {
    @objc(openTab:)
    func openTab(_ command: CDVInvokedUrlCommand) {
        var pluginResult = CDVPluginResult(
            status: CDVCommandStatus_ERROR
        )
        let parameters: AnyObject = command.arguments[0] as AnyObject
        let url: String = parameters["link"] as! String
        let tabColor: String? = parameters["tabColor"] as? String
        let animation: String? = parameters["animation"] as? String
        
        if(!url.isEmpty) {
            let safariTab: SFSafariViewController? = createSafariViewController(url, tabColor)
            if (safariTab != nil) {
                if (animation ?? "").isEmpty {
                    self.viewController.present(safariTab!, animated: false)
                } else {
                    self.viewController.present(safariTab!, animated: true)
                }
                pluginResult = CDVPluginResult(
                    status: CDVCommandStatus_OK
                )
            }
        }
        self.commandDelegate!.send(
            pluginResult,
            callbackId: command.callbackId
        )
    }
    
    private func createSafariViewController(_ link: String, _ color: String?) -> SFSafariViewController? {
        let validatedURL = validateURL(link)
        if let url: URL = URL(string: validatedURL) {
            var vc: SFSafariViewController
            if #available(iOS 11.0, *) {
                let config: SFSafariViewController.Configuration = SFSafariViewController.Configuration()
                config.entersReaderIfAvailable = true
                vc = SFSafariViewController(url: url, configuration: config)
            } else {
                vc = SFSafariViewController(url: url)
            }
            
            if #available(iOS 10.0, *) {
                vc.preferredBarTintColor = createUIColor(color)
            }
            return vc
        }
        return nil
    }
    
    private func validateURL(_ url: String) -> String {
        var validURL: String = url;
        if !url.hasPrefix("https://") && !url.hasPrefix("http://") {
            validURL = "https://" + url;
        }
        return validURL;
    }
    
    func createUIColor (_ hex: String?) -> UIColor {
        if hex == nil {
            return UIColor.white
        }
        var cString:String = hex!.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }
        
        if ((cString.count) != 6) {
            return UIColor.white
        }
        
        var rgbValue:UInt32 = 0
        Scanner(string: cString).scanHexInt32(&rgbValue)
        
        return UIColor(
            red: CGFloat((rgbValue >> 16) & 0xFF) / 255.0,
            green: CGFloat((rgbValue >> 8) & 0xFF) / 255.0,
            blue: CGFloat(rgbValue & 0xFF) / 255.0,
            alpha: 1.0
        )
    }
}
