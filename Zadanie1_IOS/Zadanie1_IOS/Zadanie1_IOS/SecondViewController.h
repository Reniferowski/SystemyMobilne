//
//  SecondViewController.h
//  Zadanie1_IOS
//
//  Created by student on 05/12/2022.
//  Copyright © 2022 example. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@class SecondViewController;

@protocol SecondViewControllerDelegate <NSObject>
-(void) addItemViewController:(SecondViewController *) controller didFinishEnteringItem: (NSString *) item;
@end

@interface SecondViewController : UIViewController
@property (nonatomic, weak) IBOutlet UITextField *modifiedSurnameTextField;
@property NSString *surname;
@property (nonatomic, weak) IBOutlet UIButton *goBackButton;
@property (nonatomic, weak) id <SecondViewControllerDelegate> delegate;

@end

NS_ASSUME_NONNULL_END
