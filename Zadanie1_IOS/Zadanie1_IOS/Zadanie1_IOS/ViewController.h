//
//  ViewController.h
//  Zadanie1_IOS
//
//  Created by student on 05/12/2022.
//  Copyright © 2022 example. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondViewController.h"

@interface ViewController : UIViewController <SecondViewControllerDelegate>
@property (nonatomic, weak) IBOutlet UILabel *messageLabel;
@property (nonatomic, weak) IBOutlet UITextField *InputField;
@property (nonatomic, weak) IBOutlet UITextField *surnameTextField;

-(IBAction)enter;
-(void) addItemViewController;

@end

